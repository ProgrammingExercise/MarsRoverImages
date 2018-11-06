# Mars Rover Images

This is an ASP.NET Core application that pulls images from the NASA mars rover API for specific dates and displays them in a webpage.

## Dependencies

- Visual Studio 2017
- Docker
- IntelliJ

## Building and Running the application

To run the project open the solution in Visual Studio 2017, make sure that the *docker-compose* project is selected as the startup project, and then hit the run button. 

## Changing the dates used to pull images

To change the dates, edit the dates.txt file in the wwwroot/data folder. Each date should be on a seperate line.

## Running Tests

To run the tests

- Start the web application 
- Open the Selenium Test project in IntelliJ
- Make sure the port number in the project is correct
- Right click on the test you want to run and click run.
