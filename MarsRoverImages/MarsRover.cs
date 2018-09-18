using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace MarsRoverImages
{
    [DataContract(Name ="rover")]
    public class MarsRover
    {
        [DataMember(Name = "id")]
        public long Id { get; set; }

        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "landing_date")]
        public string LandingDateStr { get; set; }

        [DataMember(Name = "launch_date")]
        public string LaunchDateStr { get; set; }

        [DataMember(Name = "status")]
        public string Status { get; set; }

        [DataMember(Name = "max_sol")]
        public string MaxSol { get; set; }

        [DataMember(Name = "max_date")]
        public string MaxDateStr { get; set; }

        [DataMember(Name = "total_photos")]
        public long TotalPhotos { get; set; }

        [DataMember(Name = "cameras")]
        public List<RoverCamera> Cameras { get; set; }

        public MarsRover()
        {
        }
    }
}
