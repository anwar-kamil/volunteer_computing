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
};

#define BACKLOG 3
sys_info sysinfo;

int parse(char * b);
char * server_connect();
void sysinfo_val(char * buffer);

int main(void)
{
char * buffer = new char[100];
strcpy(buffer,server_connect());
sysinfo_val(buffer);
return 0;
}

void sysinfo_val(char * buffer)
{
char * cpu_buf, * mem_buf; 
cpu_buf = mem_buf = new char[100];
cpu_buf=strtok(buffer,"\n");
mem_buf=strtok(NULL,"\n");
sysinfo.cpu_mhz=parse(cpu_buf);
sysinfo.mem_kb=parse(mem_buf);
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

printf("server: waiting for connections...\n");
while(1) { // main accept() loop
sin_size = sizeof their_addr;
new_fd = accept(sock, (struct sockaddr *)&their_addr, &sin_size);
if (new_fd == -1) {
perror("accept");
continue;
}
sleep (1);
 if ((recv(new_fd, buffer, sizeof (buffer), 0)) == -1) 
perror("recv");
//else
//printf ("%s\n", buffer);

close(new_fd);
return buffer;
}
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
