using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Owin.Hosting;

namespace ForTwo.Api.OWIN
{
    public class Program
    {
        public static void Main(string[] args)
        {

            string url = "http://+:8081/";

            using (WebApp.Start(url))
            {
                Console.WriteLine("Api started on : " + url);
                Console.ReadKey();
            }
        }
    }
}
