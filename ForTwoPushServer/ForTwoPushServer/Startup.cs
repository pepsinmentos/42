using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using Autofac;
using Autofac.Integration.SignalR;
using Microsoft.AspNet.SignalR;
using Microsoft.Owin;
using Microsoft.Owin.Cors;
using Owin;

namespace ForTwoPushServer
{
    public class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            var builder = new ContainerBuilder();
            builder.RegisterHubs(Assembly.GetExecutingAssembly());
            builder.RegisterInstance(new Dictionary<int, string>()).As<Dictionary<int, string>>().SingleInstance();

            var config = new HubConfiguration();
            var container = builder.Build();
            config.Resolver = new AutofacDependencyResolver(container);


            
            app.UseCors(CorsOptions.AllowAll);
            app.MapSignalR(config);
        }
    }
}
