#! bin/bash/

for i in src/protocols/*.avpr; do
    echo $i
    java -jar lib/avro-tools-1.7.7.jar compile protocol $i src
    
done