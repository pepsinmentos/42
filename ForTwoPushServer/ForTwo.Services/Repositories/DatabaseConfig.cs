using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Services.Interfaces;

namespace ForTwo.Services.Repositories
{
    public class DatabaseConfig : IDatabaseConfig
    {
        public string ConnectionString { get; set; }
    }
}