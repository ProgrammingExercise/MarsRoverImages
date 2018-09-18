using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace MarsRoverImages
{
    [DataContract(Name ="data")]
    public class MarsData
    {
        [DataMember(Name = "photos")]
        public List<MarsPhoto> Photos { get; set; }

        public MarsData()
        {
        }
    }
}
