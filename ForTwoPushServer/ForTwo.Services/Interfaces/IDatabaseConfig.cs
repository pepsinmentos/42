﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ForTwo.Services.Interfaces
{
    public interface IDatabaseConfig
    {
        string ConnectionString { get; set; }
    }
}
