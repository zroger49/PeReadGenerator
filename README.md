# PeReadGenerator
A simple generator of pair-end reads built with java 


# Usage 

java -jar PeReadGenerator.jar -i input_file -o output_file_prefix -n numberofReads -s readSize -f fragmentSize -e ErrorRate 


## Note on error rate model: 
The error rate model implemented is very simple. Currently, the substitution matrix for each nucleotide is the same (meaning there is an equal probabilty of A -> G and A -> T).

To improve computation speed, instead of testing each nucleotide, the number expected erros is computed (x = numberOfReads * 2 * readsSize * errorRate), and a loop is performed x times, where the strand, reads and position of the error are radomly picked



