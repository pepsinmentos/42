using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Dapper;
using ForTwo.Services.Interfaces;

namespace ForTwo.Services.Repositories.SQL
{
    public class BaseRepositorySql
    {
        protected IDatabaseConfig DatabaseConfig;

        public BaseRepositorySql(IDatabaseConfig config)
        {
            DatabaseConfig = config;
        }

        protected List<T> GetList<T>(string sql, object parameters)
        {
            using (SqlConnection con = new SqlConnection(DatabaseConfig.ConnectionString))
            {
                var result = con.Query<T>(sql, parameters, null, false, null, System.Data.CommandType.StoredProcedure);
                if (result != null)
                    return result.ToList();
                else
                    return new List<T>();
            }
        }

        protected T GetOne<T>(string sql, object parameters)
        {
            using (SqlConnection con = new SqlConnection(DatabaseConfig.ConnectionString))
            {
                var result = con.Query<T>(sql, parameters, commandType: CommandType.StoredProcedure);
                if (result != null)
                    return result.First();

                return default(T);
            }
        }

        protected void Execute(string sql, object parameters)
        {
            using (SqlConnection con = new SqlConnection(DatabaseConfig.ConnectionString))
            {
                con.Execute(sql, parameters, commandType: CommandType.StoredProcedure);
            }
        }
    }
}
