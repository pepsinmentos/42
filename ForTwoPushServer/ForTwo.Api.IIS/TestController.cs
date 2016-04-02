using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace ForTwo.Api.IIS
{
    public class TestController : ApiController
    {
        [HttpGet]
        public string Get()
        {
            return "string";
        }
    }
}