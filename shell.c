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

using namespace std;

int makeargv(const char *s, const char *delimiters, char ***argvp);

int main(void)
{
char * s= new char[30];
char ** t;

for(;;)
    {    
cout<<"\nharis@ubuntu-Mr. CR:~$ ";
gets(s);
int i;

//Memory allocation for the array of arguments
if ((t = (char**)malloc(strlen(s) + 1)) == NULL)
      return -1;
//Tokenization of input for the array of arguments
     *t = strtok(s, " ");
      for (i = 1; ; i++){
          *((t) + i) = strtok(NULL, " ");
	  if(*((t) + i)==NULL)break;}

//Checking if the user wants to exit
  if(strcmp(s,"quit")==0)
    return 0;

//else creating a child process
    pid_t p=fork();
    if(!p)//Child Process code
       {
	if (-1 == execvp(t[0],t))
	perror(t[0]);
       }
    else{//Parent Process code
	if(strcmp(*((t) + (i-1)),"&")==0)
	cout<<"\n\nParent is not waiting for child \n\n";
	else{

	int status,childpid;
//Waiting for the child
	while (((childpid = waitpid(p,&status,NULL)) == -1) && (errno == EINTR)) ;

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
	 }
    }
return 0;
}
