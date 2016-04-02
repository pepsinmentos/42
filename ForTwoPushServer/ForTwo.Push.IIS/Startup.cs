using System.Collections.Generic;
using Autofac;
using ForTwo.Hubs;
using ForTwo.Push.IIS;
using Microsoft.AspNet.SignalR;
using Microsoft.Owin;
using Microsoft.Owin.Cors;
using Owin;
using Autofac.Integration.SignalR;

[assembly: OwinStartup(typeof (Startup))]

namespace ForTwo.Push.IIS
{
    public class Startup
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