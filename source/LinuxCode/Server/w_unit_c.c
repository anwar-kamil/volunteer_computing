#include <stdio.h>

int main()
{
FILE * f;
f = fopen("output.txt", "w");
for(int i=0; i<10000; i++)
fprintf(f,"%d\n",i);
fclose(f);
return 0;
}
