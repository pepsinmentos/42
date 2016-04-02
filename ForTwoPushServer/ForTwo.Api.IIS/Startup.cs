using System;
using System.Collections.Generic;
using System.Linq;
using Autofac;
using Autofac.Integration.SignalR;
using ForTwo.Hubs;
using Microsoft.AspNet.SignalR;
using Microsoft.Owin;
using Owin;
using Microsoft.Owin.Cors;

[assembly: OwinStartup(typeof(ForTwo.Api.IIS.Startup))]

namespace ForTwo.Api.IIS
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            var hubConfig = new HubConfiguration();
            var builder = new ContainerBuilder();
            builder.RegisterHubs(IoC.GetAssembly());
            builder.RegisterInstance(new Dictionary<int, string>()).As<Dictionary<int, string>>().SingleInstance();

            var container = builder.Build();
            hubConfig.Resolver = new AutofacDependencyResolver(container);

            app.UseCors(CorsOptions.AllowAll);
            app.MapSignalR(hubConfig);
        }
    }
}
