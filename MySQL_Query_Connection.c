#include <my_global.h>
#include <mysql.h>

int main(int argc, char **argv)
{

  MYSQL *conn;
  MYSQL_RES *result;
  MYSQL_ROW row;
  MYSQL_FIELD *field;
  int i;
  int num_fields;
  int num_rows;
  
//initializing the MySQL connection
  conn = mysql_init(NULL);
  if (conn == NULL) {
      printf("Error %u: %s\n", mysql_errno(conn), mysql_error(conn));
      exit(1);
  }

//establishing the connection
  if (mysql_real_connect(conn, "localhost", "root", 
          "root", NULL, 0, NULL, 0) == NULL) {
      printf("Error %u: %s\n", mysql_errno(conn), mysql_error(conn));
      exit(1);
  }

//running the query
  if (mysql_query(conn, "show databases")) {
      printf("Error %u: %s\n", mysql_errno(conn), mysql_error(conn));
      exit(1);
  }
  
//Fetching the resulted data from last query
  result = mysql_store_result(conn);

  printf("\n");
//Finding the number of columns in the fetched query
  num_fields = mysql_num_fields(result);
  printf("Number of Columns: %d\n",num_fields);
  printf("\n");

//Finding the number of rows in the fetched query
  num_rows = mysql_num_rows(result);
  printf("Number of Rows: %d\n",num_rows);
  printf("\n");
  
//Fetching the Columns One by One from resulted data
  printf("Printing Column Names:\n");
     while(field = mysql_fetch_field(result)) {
        printf("%s ", field->name);
     }
  printf("\n\n");

//Fetching the rows One by One from resulted data
  printf("Printing Rows:\n");
  while ((row = mysql_fetch_row(result)))
  {
      for(i = 0; i < num_fields; i++)
      {
          printf("%s  ", row[i] ? row[i] : "NULL");
      }
  printf("\n");
  }

//Releasing the last query
  mysql_free_result(result);

//Closing the connection
  mysql_close(conn);

}
