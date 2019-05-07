#include <stdlib.h>
#include <stdio.h>
#include <netinet/in.h>
#include <unistd.h>
 #include <arpa/inet.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <iostream>

using namespace std;

void set_id(int i);
int parse(char * b);
char * cpu_file();
char * mem_file();
char * cpu_info();
char * id();

int main(int argc, char *argv[])
{
  int sock;
  struct sockaddr_in sa;
  int bytes, buffer_length;
  char buffer[1024];
 
char * cpuinfo= new char [100];
strcpy(cpuinfo,cpu_info());

  sock=socket(PF_INET, SOCK_STREAM, 0);
  if (sock ==-1)
 {
      printf("Error Creating Socket");
      exit(EXIT_FAILURE);
 }
 
  memset(&sa, 0, sizeof sa);
  sa.sin_family = AF_INET;
  sa.sin_addr.s_addr = inet_addr("127.0.0.1");
  sa.sin_port = htons(6000);
  if (connect(sock, (struct sockaddr*)&sa, sizeof sa) == -1) {
close(sock);
perror("client: connect");
//continue;
}

char * cid = new char[10];
cid=id();
strcpy(cid,"0");
cout<<"sending to server: "<<cid;
if (send(sock, cid, 100, 0) == -1)
perror("send");
 if ((bytes = recv(sock, buffer, sizeof (buffer), 0)) == -1) 
perror("recv");
else
printf ("%s\n", buffer);
int nid = parse(buffer);
if(nid>0)
set_id(nid);
//if (send(sock, cpuinfo, 100, 0) == -1)
//perror("send");

  close(sock);

  return 0;
}

char * id()
{
   FILE * f1;
   f1 = fopen("config.txt","r");
   if(f1 == NULL)
   {perror("file1");exit(1);}

size_t * s1=new size_t;
char * buffer = new char [100];
getline(&buffer, s1, f1);
cout<<"input from file "<<buffer<<" ends.";

fclose(f1);
return buffer;
}

char * cpu_info()
{
char* result = new char[100];
strcpy(result,cpu_file());
strcat(result,mem_file());
return result;
}

char* cpu_file()
{
   FILE * file1;
   file1 = fopen("//proc//cpuinfo","r");
   if(file1 == NULL)
   {perror("file1");exit(1);}

size_t s=100; size_t * s1=new size_t;
char * buffer = new char [100];
while(1)
{
getline(&buffer, s1,file1);
if(buffer[0]=='c' && buffer[1]=='p' && buffer[2]=='u' && buffer[3]==' ' && buffer[4]=='M' && buffer[5]=='H' && buffer[6]=='z')break;
} 

fclose(file1);
return buffer;
}

char* mem_file()
{
   FILE * file1;
   file1 = fopen("//proc//meminfo","r");
   if(file1 == NULL)
   {perror("file1");exit(1);}

size_t * s1=new size_t;
char * buffer = new char [100];
getline(&buffer, s1,file1);

fclose(file1);
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

