@cd proto
@for %%n in (*.proto) do  @..\protoc %%n --cpp_out=..\cpp
@cd ..