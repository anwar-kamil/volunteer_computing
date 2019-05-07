#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h> 
#include <stdlib.h> 
#include <time.h>
#include <arpa/inet.h>
#include <iostream>

using namespace std;

void set_id(int i);
int parse(char * b);
char * cpu_file();
char * mem_file();
char * cpu_info();
char * id();
void wait_error(pid_t p, char t);
double algo_run(char * file_name);

int main(int argc, char *argv[])
{
  int sock;
  struct sockaddr_in sa;
  int bytes, buffer_length;
  char buffer[1024];
 
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
cout<<"\nSending own Id to Server: "<<cid;
if (send(sock, cid, 100, 0) == -1)
perror("send");
if ((bytes = recv(sock, buffer, sizeof (buffer), 0)) == -1) 
perror("recv");
else
printf ("\nValue recieved from Server: %s\n", buffer);
int nid = parse(buffer);
if(nid>0)
set_id(nid);

char * cpuinfo= new char [100];
strcpy(cpuinfo,cpu_info());
cout<<"Sending following Cpu Info to Server: \n"<<cpuinfo;
if (send(sock, cpuinfo, 100, 0) == -1)
perror("send");

if ((bytes = recv(sock, buffer, sizeof (buffer), 0)) == -1) 
perror("recv");
printf ("\nValue recieved from Server: %s\n", buffer);

//cout<<"\nPlease wait while given algorithm executes...";
//cout<<"\nAlgorithm executed in : "<<algo_run("gen.c")<<" seconds.\n";

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

double algo_run(char *f)
{
time_t startTime = time(NULL); // return current time in seconds
pid_t p;
double tot_time;
//creating a child
printf("\n");
p=fork();

if(!p)//Child Process code
{
if (-1 == execlp("g++", "g++", f, "-o", "r", NULL))
	{
	perror("g++ compiler error: ");
	printf("Exec Error");
	return 0;
	}
}
else
{//Parent Process code
printf("Waiting for child: ");
wait_error(p,'n');
p=fork();

	if(!p)//Child Process code
	{
	if (-1 == execlp("./r", "./r", NULL))
		{
		perror("Program Executing Error: ");
		printf("Exec Error");
		return 0;
		}
	}
	else
	{//Parent Process code
	printf("Waiting for child: ");
	wait_error(p,'n');
        }
time_t endTime = time(NULL); // return current time in seconds	
tot_time=(double)(endTime-startTime);
}
return tot_time;
}

void wait_error(pid_t p, char t)
{
	int status,childpid;
//Waiting for the child
if(t=='n')
	while (((childpid = waitpid(p,&status,NULL)) == -1) && (errno == EINTR)) ;
else if(t=='h')
	while (((childpid = waitpid(p,&status,WNOHANG)) == -1) && (errno == EINTR)) ;

//If waiting for the child sends signals then show error
   if (childpid == -1)
      perror("Failed to wait for child");
   else if (WIFEXITED(status) && !WEXITSTATUS(status))
      printf("Child %ld terminated normally\n", (long)childpid);
   else if (WIFEXITED(status))
      printf("Child %ld terminated with return status %d\n",
             (long)childpid, WEXITSTATUS(status));
   else if (WIFSIGNALED(status))
      printf("Child %ld terminated due to uncaught signal %d\n",
             (long)childpid, WTERMSIG(status));
   else if (WIFSTOPPED(status))
      printf("Child %ld stopped due to signal %d\n",
             (long)childpid, WSTOPSIG(status));
}
