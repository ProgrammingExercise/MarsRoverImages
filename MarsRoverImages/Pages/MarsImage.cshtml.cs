using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.FileProviders;

namespace MarsRoverImages.Pages
{
    public class MarsImageModel : PageModel
    {
        private readonly IFileProvider _fileProvider;

        public string LocalUrl = String.Empty;
        public string PhotoId = String.Empty;
        public string Camera = String.Empty;
        public string EarthDate = String.Empty;
        public string Rover = String.Empty;

        public MarsImageModel(IFileProvider fileProvider)
        {
            _fileProvider = fileProvider;
        }
        public async Task OnGet(string localUrl, string photoId, string camera, string earthDate, string rover)
        {
            LocalUrl = localUrl;
            PhotoId = photoId;
            Camera = camera;
            EarthDate = earthDate;
            Rover = rover;
        }
    }
}
