﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Owin.Hosting;

namespace ForTwoPushServer
{
    static class Program
    {
        public static void Main(string[] args){

            string url = "http://localhost:8080";
            using (WebApp.Start(url))
            {
                Console.WriteLine("Server running on {0}", url);
                Console.ReadKey();

            }
            
            
        }
    }
}
