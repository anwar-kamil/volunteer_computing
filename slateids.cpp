#include<iostream>
#include<string.h>
#include<fstream>
using namespace std;

int main()
{
   FILE * f;
   f = fopen("PM IDS.txt","r");
   if(f == NULL)
   {perror("f");return 1;}
char * id[44];
for(int i=0; i<44; i++)id[i]=new char[20];

size_t s=100; size_t * s1=new size_t;
char * buffer = new char [100];

   FILE * of;
   of = fopen("IDS.txt","w");

for(int ic=0; ic<=43; ic++)
{
getline(&buffer, s1,f);
	for(int i=0; i<strlen(buffer); i++)
	{	
	if(buffer[i]=='@')
		{
		id[ic][7]='@';
		for(int j=6; j>=0; j--)
			{
			id[ic][j]=buffer[i-((j-7)*(-1))];
			}
	strcat(id[ic],"nu.edu.pk");
	fprintf(of,"%s,\n",id[ic]);
		}
	}
}

fclose(f);
return 0;
}
