<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="actionType" placeholder="请输入操作类型" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="ipAddress" placeholder="请输入IP地址" suffix-icon="el-icon-search" style="width: 200px; margin-left: 5px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>

    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border>
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="userId" label="操作用户ID" width="120"></el-table-column>
      <el-table-column prop="actionType" label="操作类型" width="120"></el-table-column>
      <el-table-column prop="actionDetail" label="操作详情" width="200"></el-table-column>
      <el-table-column prop="requestData" label="操作数据"></el-table-column>
      <el-table-column prop="ipAddress" label="IP地址" width="150"></el-table-column>
      <el-table-column prop="actionTime" label="操作时间" width="180"></el-table-column>
      <el-table-column prop="operate" label="操作" width="100">
        <template slot-scope="scope">
          <el-popconfirm
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
              style="margin-left: 5px;"
          >
            <el-button slot="reference" size="small" type="danger" >删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20, 30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "SystemLog",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      // 搜索参数
      actionType: '',
      ipAddress: '',
    }
  },
  methods: {
    del(id){
      this.$axios.get(this.$httpUrl+'/systemlog/del?id='+id).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },
    handleSizeChange(val) {
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      this.pageNum=val
      this.loadPost()
    },
    resetParam(){
      this.actionType=''
      this.ipAddress=''
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/systemlog/listPageC1',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          actionType: this.actionType,
          ipAddress: this.ipAddress,
        }
      }).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert('获取数据失败')
        }
      })
    }
  },
  beforeMount() {
    this.loadPost()
  }
}
</script>

<style scoped>

</style>