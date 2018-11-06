/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.codehaus.groovy.transform.tailrec

import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.syntax.Token
import org.junit.Before
import org.junit.Test

/**
 * @author Johannes Link
 */
class VariableExpressionReplacerTest {

    static final Token EQUALS = Token.newSymbol("==", -1, -1)

    VariableExpressionReplacer replacer
    def replacements = [:]
    Closure<Boolean> when = { VariableExpression variableExpression -> replacements.containsKey variableExpression }
    Closure<VariableExpression> replaceWith = { VariableExpression variableExpression -> replacements[variableExpression] }

    @Before
    void init() {
        replacer = new VariableExpressionReplacer(when: when, replaceWith: replaceWith)
    }

    @Test
    public void replaceInReturnStatement() {
        def createStatement = { new ReturnStatement(it) }
        def accessExpression = { it.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceEmbeddedInBooleanExpression() {
        def createStatement = { new ReturnStatement(new BooleanExpression(it)) }
        def accessExpression = { it.expression.expression }

        assertReplace(createStatement, accessExpression)
    }


    @Test
    public void replaceDeeplyEmbeddedInReturnStatement() {
        def createStatement = { new ReturnStatement(new BooleanExpression(new BinaryExpression(it, EQUALS, aConstant('a')))) }
        def accessExpression = { it.expression.expression.leftExpression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceBooleanExpressionInIfElseStatement() {
        def createStatement = { new IfStatement(new BooleanExpression(it), anEmptyStatement(), anEmptyStatement()) }
        def accessExpression = { it.booleanExpression.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceCollectionExpressionInForLoop() {
        def createStatement = { new ForStatement(anyParameter(), it, anEmptyStatement()) }
        def accessExpression = { it.collectionExpression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceBooleanExpressionInWhileLoop() {
        def createStatement = { VariableExpression toReplace -> new WhileStatement(new BooleanExpression(toReplace), anEmptyStatement()) }
        def accessExpression = { WhileStatement statement -> statement.booleanExpression.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceBooleanExpressionInDoWhileLoop() {
        def createStatement = { VariableExpression toReplace -> new DoWhileStatement(new BooleanExpression(toReplace), anEmptyStatement()) }
        def accessExpression = { DoWhileStatement statement -> statement.booleanExpression.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceExpressionInSwitch() {
        def createStatement = { VariableExpression toReplace -> new SwitchStatement(toReplace, anEmptyStatement()) }
        def accessExpression = { SwitchStatement statement -> statement.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceExpressionInCase() {
        def createStatement = { VariableExpression toReplace -> new CaseStatement(toReplace, anEmptyStatement()) }
        def accessExpression = { CaseStatement statement -> statement.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceExpressionInExpressionStatement() {
        def createStatement = { VariableExpression toReplace -> new ExpressionStatement(toReplace) }
        def accessExpression = { ExpressionStatement statement -> statement.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceExpressionInThrowStatement() {
        def createStatement = { VariableExpression toReplace -> new ThrowStatement(toReplace) }
        def accessExpression = { ThrowStatement statement -> statement.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceBooleanExpressionInAssertStatement() {
        def createStatement = { VariableExpression toReplace -> new AssertStatement(new BooleanExpression(toReplace), aVariable('any')) }
        def accessExpression = { AssertStatement statement -> statement.booleanExpression.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceMessageExpressionInAssertStatement() {
        def createStatement = { VariableExpression toReplace -> new AssertStatement(new BooleanExpression(aVariable('any')), toReplace) }
        def accessExpression = { AssertStatement statement -> statement.messageExpression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceExpressionInSynchronizedStatement() {
        def createStatement = { VariableExpression toReplace -> new SynchronizedStatement(toReplace, anEmptyStatement()) }
        def accessExpression = { SynchronizedStatement statement -> statement.expression }

        assertReplace(createStatement, accessExpression)
    }

    @Test
    public void replaceOnlyRightExpressionInBinaryExpression() {
        def toReplace = aVariable("old")
        def replacement = aVariable("new")
        def binaryExpression = new BinaryExpression(toReplace, EQUALS, toReplace)
        replacements[toReplace] = replacement
        replacer.replaceIn(binaryExpression)
        assert binaryExpression.rightExpression == replacement
        assert binaryExpression.leftExpression == toReplace
    }

    private void assertReplace(Closure<Statement> createStatement, Closure<Expression> accessExpression) {
        def toReplace = aVariable("old")
        toReplace.lineNumber = 42
        def replacement = aVariable("new")
        def statement = createStatement(toReplace)

        replacements[toReplace] = replacement
        replacer.replaceIn(statement)

        assert accessExpression(statement) == replacement
        assert accessExpression(statement).lineNumber == replacement.lineNumber
    }


    def anEmptyStatement() {
        new EmptyStatement()
    }

    def aConstant(value) {
        new ConstantExpression(value)
    }

    def aVariable(value) {
        new VariableExpression(value)
    }

    def anyParameter() {
        new Parameter(ClassHelper.int_TYPE, 'a')
    }

}
