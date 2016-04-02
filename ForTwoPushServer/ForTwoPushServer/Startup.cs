using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using Autofac;
using Autofac.Integration.SignalR;
using ForTwo.Hubs;
using Microsoft.AspNet.SignalR;
using Microsoft.Owin;
using Microsoft.Owin.Cors;
using Owin;
using Autofac.Integration.WebApi;

namespace ForTwoPushServer
{
    public class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            var builder = new ContainerBuilder();
            builder.RegisterHubs(IoC.GetAssembly());
            builder.RegisterInstance(new Dictionary<int, string>()).As<Dictionary<int, string>>().SingleInstance();

            var signalrConfig = new HubConfiguration();
            
            app.UseCors(CorsOptions.AllowAll);

            ForTwo.Api.IoCRegistry.Configure(builder);
          

            HttpConfiguration config = new HttpConfiguration();

            config.Formatters.JsonFormatter.SupportedMediaTypes.Add(new MediaTypeHeaderValue("text/html"));
            config.Routes.MapHttpRoute(name: "DefaultApi", routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional });

          
            

            
              var container = builder.Build();
            signalrConfig.Resolver = new AutofacDependencyResolver(container);
            config.DependencyResolver = new AutofacWebApiDependencyResolver(container);

            app.UseWebApi(config);
            app.MapSignalR(signalrConfig);
        }
    }
}
