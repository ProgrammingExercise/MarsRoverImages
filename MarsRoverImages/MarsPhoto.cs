using System;
using System.Runtime.Serialization;

namespace MarsRoverImages
{
    [DataContract(Name="photo")]
    public class MarsPhoto
    {
        [DataMember(Name ="id")]
        public long PhotoId { get; set; }

        [DataMember(Name = "sol")]
        public long Sol { get; set; }

        [DataMember(Name = "camera")]
        public RoverCamera Camera { get; set; }

        [DataMember(Name = "img_src")]
        public Uri ImageSource { get; set; }

        [DataMember(Name = "earth_date")]
        public string EarthDateStr { get; set; }

        [DataMember(Name = "rover")]
        public MarsRover Rover { get; set; }

        public MarsPhoto()
        {
        }
    }
}
