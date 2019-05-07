#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h> 
#include <stdlib.h>
#include <arpa/inet.h>
#include <iostream>

void wait_error(pid_t p, char t);
int algo_run(char * file_name);

int main(void)
{
char * f = new char[100];
printf("Our Program will compile your source file and run it: \n");
printf("Please enter your source file: ");
gets(f);
algo_run(f);
return 0;
}

int algo_run(char *f)
{
time_t startTime = time(NULL); // return current time in seconds
pid_t p;

//creating a child
p=fork();

if(!p)//Child Process code
{
if (-1 == execlp("g++", "g++", f, "-o", "r", NULL))
	{
	perror("cat");
	printf("Exec Error");
	return 0;
	}
}
    
else
{//Parent Process code
wait_error(p,'n');
printf("Exec waiting");

p=fork();

	if(!p)//Child Process code
	{
	printf("Exec running or has done");
	if (-1 == execlp("./r", "./r", NULL))
		{
		perror("cat");
		printf("Exec Error");
		return 0;
		}
	}
	else
	{//Parent Process code
	wait_error(p,'n');
        }
printf("Waiting ends");
time_t startTime = time(NULL); // return current time in seconds	
}
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
