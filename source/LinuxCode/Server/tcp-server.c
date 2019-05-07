#include <fstream>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h> 
#include <stdlib.h>
#include <arpa/inet.h>
#include <iostream>

using namespace std;

struct sys_info{
int cpu_mhz;
int mem_kb;
char reliability; //for reliability
char sclass; //for classification
};

#define BACKLOG 3
sys_info sysinfo;

char sysinfo_val(char * buffer);
char * id_inc();
char * server_connect();
int parse(char * b);
void set_id(int i);
char class_id(int mem, int cpu, double point);
char * read_file(char * file_name);
void write_file(char * file_name, char * content);

int main(void)
{
char * buffer = new char[100];
strcpy(buffer,server_connect());
return 0;
}

char * server_connect()
{
 int new_fd;
  int sock = socket(AF_INET, SOCK_STREAM, 0);
  struct sockaddr_in sa, their_addr; 
  static char buffer [1024];
  int recsize;
  socklen_t fromlen, sin_size;
  
  memset(&sa, 0, sizeof sa);
  memset (& buffer, 0, sizeof buffer);
  sa.sin_family = AF_INET;

sa.sin_addr.s_addr=inet_addr("127.0.0.1");  
sa.sin_port = htons(6000);
 
  if (-1 == bind(sock,(struct sockaddr *)&sa, sizeof sa))
  {
    perror("error bind failed");
    close(sock);
    exit(EXIT_FAILURE);
  } 

if (listen(sock, BACKLOG) == -1) {
perror("listen");
exit(1);
}

while(1)
	{
	while(1) 
		{ // main accept() loop
	printf("server: waiting for connections...\n");
		sin_size = sizeof their_addr;
		new_fd = accept(sock, (struct sockaddr *)&their_addr, &sin_size);
		if (new_fd == -1) 
			{
			perror("accept");
			continue;
			}
		sleep (1);
		 if ((recv(new_fd, buffer, sizeof (buffer), 0)) == -1) 
		perror("recv");
		else
			{
			printf ("\nClient Id recieved: %s\n", buffer);
			if(parse(buffer)==0)
				{//send generated id to new comer
				int nid = parse(id_inc());
				int n=sprintf (buffer, "%d", nid);
				if (send(new_fd, buffer, sizeof(buffer), 0) == -1)
				perror("send");
				}
			else
				{//send 0 to already registered client, 0 indicates send CPU Info
				int nid = 0;
				int n=sprintf (buffer, "%d", nid);
				if (send(new_fd, buffer, sizeof(buffer), 0) == -1)
				perror("send");
				}
			 if ((recv(new_fd, buffer, sizeof (buffer), 0)) == -1) 
			perror("recv");
			printf ("\nClient Device info recieved:\n");
			cout<<buffer;
			char ssclass=sysinfo_val(buffer);

			char * filename = new char[10000];
			if(ssclass=='a')
			strcpy (filename, "w_unit_a.c");
			else if(ssclass=='b')
			strcpy (filename, "w_unit_b.c");
			else
			strcpy (filename, "w_unit_c.c");

			printf ("\nAlgorithm File sending to Client: %s\n", filename);
			if (send(new_fd, filename, sizeof(buffer), 0) == -1)
			perror("send");

			strcpy(buffer,read_file(filename));
			if (send(new_fd, buffer, sizeof(buffer), 0) == -1)
			perror("send");

			if ((recv(new_fd, filename, 10000, 0)) == -1) 
			perror("recv");
			printf ("\nRecieving Output File from Client: %s\n\n", filename);

			char * buffer1 = new char[100000000];
			if ((recv(new_fd, buffer1, 1000000000, 0)) == -1) 
			perror("recv");
			write_file(filename,buffer1);
			}
		}
	}
	close(new_fd);
	close(sock);
	return buffer;
}

int parse(char * b)
{
char * c = new char[200];
int tot=0;
int inc=1;
int iarr[10]; int iarri=0;
for(int i=0; b[i]!='\0'; i++)
  {
  if(b[i]>=46 && b[i]<=57)
	{
    	 if(b[i]!=46 && b[i]!=10)
		{
		iarr[iarri++]=(int)b[i]-48;
		}
	 else break;
	}
  }
for(iarri--; iarri>=0; iarri--)
	{
	int val=iarr[iarri];
	tot=tot+inc*val;
	inc*=10;
	}	
return tot;
}

void set_id(int i)
{
   FILE * f1;
   f1 = fopen("config.txt","w");
   if(f1 == NULL)
   {perror("file1");exit(1);}

size_t * s1=new size_t;
char * buffer = new char [100];
sprintf(buffer, "%d", i);
fprintf(f1, "%s", buffer);
cout<< "output in file" << buffer;
fclose(f1);
}

char class_id(int mem, int cpu, double rel_point)
{
char ssclass;
double sclass=(mem/1000000)*(cpu/1000)*rel_point;

cout<<"\nThe Formulated values is: "<<sclass<<" and ";
if(sclass<= 2)
ssclass='c';
else if(sclass>2 && sclass<=3)
ssclass='b';
else if(sclass>3)
ssclass='a';
return ssclass;
}

char * read_file(char * file_name)
{
   FILE * file1;
   file1 = fopen(file_name,"r");
   if(file1 == NULL)
   {perror("read file1");}
size_t * s1=new size_t;
char * buffer = new char [100];
char * buffer1 = new char [1000];
strcpy(buffer1, "");
while( (getline(&buffer, s1, file1)) !=-1 )
strcat(buffer1, buffer);

fclose(file1);
return buffer1;
}

void write_file(char * buffer, char * buffer1)
{
   FILE * file1;
   file1 = fopen(buffer,"w");
   if(file1 == NULL)
   {perror("file1");exit(1);}
fprintf(file1, "%s", buffer1);
fclose(file1);
}

char * id_inc()
{
  FILE * f1;
   f1 = fopen("config.txt", "r");
   if(f1 == NULL)
   {perror("file1");exit(1);}

int i=9;
size_t * s1=new size_t;
char * buffer = new char [100];
getline(&buffer, s1, f1);
cout<<"input from file "<<buffer<<" ends.";
fclose(f1);

i=parse(buffer);
i++;
set_id(i);
cout<<"Putting in file<<"<<i;

int n=sprintf (buffer, "%d", i); 
return buffer;
}

char sysinfo_val(char * buffer)
{
char * cpu_buf, * mem_buf; 
cpu_buf = mem_buf = new char[100];
cpu_buf=strtok(buffer,"\n");
mem_buf=strtok(NULL,"\n");
sysinfo.cpu_mhz=parse(cpu_buf);
sysinfo.mem_kb=parse(mem_buf);
sysinfo.sclass=class_id(sysinfo.mem_kb,sysinfo.cpu_mhz,0.7); //for classification
cout<<"Client's class is calculated to be: "<< sysinfo.sclass <<endl;
return sysinfo.sclass;
}	
