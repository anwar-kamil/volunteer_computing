//random number generator
#include<iostream>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <stdio.h>

using namespace std;

int main()
{
  FILE * file1;
   file1 = fopen("number_40000.txt","w");
   if(file1 == NULL)
   {perror("file1");exit(1);}
int num;
char buf[100];
srand(time(NULL));
for(int i=0; i<40000; i++)
	{
	num=rand() % 1000000;
	snprintf(buf, sizeof(buf), "%d\n", num);
	fprintf(file1,buf,100);
	}
return 0;
}
