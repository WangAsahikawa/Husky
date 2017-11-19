@cd proto
@for %%n in (*.proto) do  @..\protoc %%n --java_out=..\src 
@cd ..