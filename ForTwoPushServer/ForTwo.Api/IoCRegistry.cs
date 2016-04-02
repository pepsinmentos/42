using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Autofac;
using Autofac.Integration.WebApi;

namespace ForTwo.Api
{
    public static class IoCRegistry
    {
        public static void Configure(ContainerBuilder builder)
        {
            builder.RegisterApiControllers(Assembly.GetExecutingAssembly());
            ForTwo.Services.IoCRegistry.Configure(builder);
        }

        public static Assembly GetAssembly()
        {
            return Assembly.GetExecutingAssembly();
        }

    }
}
