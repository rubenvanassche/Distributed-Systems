#! bin/bash/

for i in src/protocols/*.avpr; do
    echo $i
    java -jar lib/avro-tools-1.8.2.jar compile protocol $i src
    
done