using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using Autofac;
using Autofac.Integration.WebApi;
using Owin;

namespace ForTwo.Api.OWIN
{
    public class Startup
    {
        public void Configuration(IAppBuilder appBuilder)
        {
            var builder = new ContainerBuilder();

            ForTwo.Api.IoCRegistry.Configure(builder);
            IContainer container = builder.Build();
            
            HttpConfiguration config = new HttpConfiguration();

            config.Formatters.JsonFormatter.SupportedMediaTypes.Add(new MediaTypeHeaderValue("text/html"));
            config.Routes.MapHttpRoute(name: "DefaultApi", routeTemplate: "{controller}/{id}",
                defaults: new { id = RouteParameter.Optional });

            config.DependencyResolver = new AutofacWebApiDependencyResolver(container);
            appBuilder.UseWebApi(config);
            
        }
    }
}
