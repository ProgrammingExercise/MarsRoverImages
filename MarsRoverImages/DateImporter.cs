using System;
using System.Collections.Generic;
using System.IO;

namespace MarsRoverImages
{
    public class DateImporter
    {
        public DateImporter()
        {
        }

        public List<DateTime> ImportDates(string data)
        {
            List<DateTime> dates = new List<DateTime>();
            string[] lines = data.Split('\n');
            foreach(var line in lines) 
            {
                DateTime date;
                if (DateTime.TryParse(line, out date))
                {
                    dates.Add(Convert.ToDateTime(line));
                }
            }

            return dates;
        }

        public List<DateTime> ImportDatesFromFile(string filePath) 
        {
            string fileContents = File.ReadAllText(filePath);
            return ImportDates(fileContents);
        }
    }
}
