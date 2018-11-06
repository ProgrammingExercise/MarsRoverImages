using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.FileProviders;

namespace MarsRoverImages.Pages
{
    public class IndexModel : PageModel
    {
        private readonly IFileProvider _fileProvider;

        public List<MarsPhoto> Photos { get; set; }

        public IndexModel(IFileProvider fileProvider) 
        {
            _fileProvider = fileProvider;
        }
        public async Task OnGet()
        {
            DateImporter dateImporter = new DateImporter();
            IFileInfo fileInfo = _fileProvider.GetFileInfo("wwwroot/data/dates.txt");
            List<DateTime> dates = dateImporter.ImportDatesFromFile(fileInfo.PhysicalPath);
            MarsImageCache cache = new MarsImageCache(_fileProvider, "wwwroot/images/mars/");
            Photos = await cache.GetImagesForDates(dates);

        }
    }
}
