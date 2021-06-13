# PeReadGenerator
A simple generator of pair-end reads build with java 


# Usage 

java -jar PeReadGenerator.jar -i input_file -o output_file_prefix -n numberofReads -s readSize -f fragmentSize -e ErrorRate 


## Note on error rate model: 
The error rate model implemented is very simple. Currently, the substitution matrix for each nucleotide is the same (meaning there is an equal probabilty of A -> G and A -> T).
To improve computation speed, instead of testing each nucleotide, the number expected erros is computed (numberOfReads * 2 * readsSize * errorRate), and a for loop is performed for each error, where the strand, reads and position is picked randomly).



### TODO:
[] Graphical interface
[] Code a SE mode
[] Fragment size variable?
[] Add adaptor sequences in reads (to all sequences? to a fraction of sequences?)
[] Implement Indels and Improve on the error rate model (different rates for substitutions for each substitution and indels).
[] Repetitive regions
[] Use BioJava module for parsing
