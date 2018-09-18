using System;
using System.Runtime.Serialization;

namespace MarsRoverImages
{
    [DataContract(Name ="camera")]
    public class RoverCamera
    {
        [DataMember(Name = "id")]
        public long Id { get; set; }

        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "rover_id")]
        public long RoverId { get; set; }

        [DataMember(Name = "full_name")]
        public string FullName { get; set; }

        public RoverCamera()
        {
        }
    }
}
