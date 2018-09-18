﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Http;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.FileProviders;

namespace MarsRoverImages
{
    public class MarsImageCache
    {
        private IFileProvider _fileProvider;
        private string _photoFolder;

        public MarsImageCache(IFileProvider fileProvider, string photoFolder)
        {
            _fileProvider = fileProvider;
            _photoFolder = photoFolder;
        }

        public async Task<List<string>> GetImageURLsForDates(List<DateTime> dates)
        {
            List<string> urls = new List<string>();
            foreach(var date in dates) 
            {
                urls.AddRange(await GetImageURLsForDate(date));
            }

            return urls;
        }

        public async Task<List<string>> GetImageURLsForDate(DateTime date)
        {
            List<string> urls = new List<string>();
            var serializer = new DataContractJsonSerializer(typeof(MarsData));
            using (var client = new HttpClient())
            {
                try
                {
                    var data = client.GetStringAsync("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=" + date.ToString("yyyy-M-d") + "&api_key=DEMO_KEY");
                    var marsData = serializer.ReadObject(new MemoryStream(Encoding.UTF8.GetBytes(await data))) as MarsData;
                    foreach(var photo in marsData.Photos)
                    {
                        var localFilename = System.IO.Path.GetFileName(photo.ImageSource.LocalPath);
                        var localPath = _photoFolder + localFilename;
                        var localFile = _fileProvider.GetFileInfo(localPath);
                        var localFolder = System.IO.Path.GetDirectoryName(_fileProvider.GetFileInfo(_photoFolder + "mars.txt").PhysicalPath.ToString());
                        if (!localFile.Exists)
                        {
                            Console.WriteLine("Dowloading Image from: " + photo.ImageSource);
                            var photoData = await client.GetByteArrayAsync(photo.ImageSource);
                            File.WriteAllBytes(localFolder + "/" + localFilename, photoData);
                        }
                        urls.Add(localPath.Replace("wwwroot/", ""));
                    }
                }
                catch(Exception ex)
                {
                    Console.WriteLine(ex.Message);
                }
            }
            return urls;
        }
    }
}