#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <iostream>

using namespace std;

int parse(char * b);
char * cpu_file();
char * mem_file();
int cpu_info();
int mem_info();

struct sys_info{
int cpu_mhz;
int mem_kb;
};

int main(void)
{
struct sys_info sysinfo;

sysinfo.cpu_mhz=cpu_info();
cout<<sysinfo.cpu_mhz<<endl;

sysinfo.mem_kb=mem_info();
cout<<sysinfo.mem_kb<<endl;

return 0;
}

int cpu_info()
{
char* result = new char[100];
strcpy(result,cpu_file());
return parse(result);
}

int mem_info()
{
char* result = new char[100];
strcpy(result,mem_file());
return parse(result);
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

size_t s=100; size_t * s1=new size_t;
char * buffer = new char [100];
getline(&buffer, s1,file1);

fclose(file1);
return buffer;
}
