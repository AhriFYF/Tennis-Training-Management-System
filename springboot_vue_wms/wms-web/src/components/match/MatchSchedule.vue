<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="groupType" placeholder="请输入组别" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="player1Id" placeholder="请输入选手1 ID" suffix-icon="el-icon-search" style="width: 200px; margin-left: 5px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="player2Id" placeholder="请输入选手2 ID" suffix-icon="el-icon-search" style="width: 200px; margin-left: 5px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>

    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border>
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="matchRound" label="轮次"></el-table-column>
      <el-table-column prop="groupType" label="组别"></el-table-column>
      <el-table-column prop="player1Id" label="选手1 ID"></el-table-column>
      <el-table-column prop="player2Id" label="选手2 ID"></el-table-column>
      <el-table-column prop="courtNumber" label="球台编号"></el-table-column>
      <el-table-column prop="matchTime" label="比赛时间" width="180"></el-table-column>

      <el-table-column prop="operate" label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
              style="margin-left: 5px;">
            <el-button slot="reference" size="small" type="danger">删除</el-button>
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

    <el-dialog
        title="月赛赛程信息"
        :visible.sync="centerDialogVisible"
        width="60%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="120px">
        <el-form-item label="轮次" prop="matchRound">
          <el-input v-model="form.matchRound"></el-input>
        </el-form-item>
        <el-form-item label="组别" prop="groupType">
          <el-input v-model="form.groupType"></el-input>
        </el-form-item>
        <el-form-item label="选手1 ID" prop="player1Id">
          <el-input v-model="form.player1Id"></el-input>
        </el-form-item>
        <el-form-item label="选手2 ID" prop="player2Id">
          <el-input v-model="form.player2Id"></el-input>
        </el-form-item>
        <el-form-item label="球台编号" prop="courtNumber">
          <el-input v-model="form.courtNumber"></el-input>
        </el-form-item>
        <el-form-item label="比赛时间" prop="matchTime">
          <el-date-picker
              v-model="form.matchTime"
              type="datetime"
              placeholder="选择比赛日期时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "MatchSchedule",
  data() {
    // 验证规则，可以根据需要调整
    let checkNumber = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('该字段不能为空'));
      }
      setTimeout(() => {
        if (!Number(value)) {
          callback(new Error('请输入数字值'));
        } else {
          callback();
        }
      }, 1000);
    };

    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      // 搜索参数
      groupType: '',
      player1Id: '',
      player2Id: '',
      centerDialogVisible: false,
      form: {
        id: '',
        matchRound: '',
        groupType: '',
        player1Id: '',
        player2Id: '',
        courtNumber: '',
        matchTime: ''
      },
      rules: {
        matchRound: [
          {required: true, message: '请输入轮次', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ],
        groupType: [
          {required: true, message: '请输入组别', trigger: 'blur'}
        ],
        player1Id: [
          {required: true, message: '请输入选手1 ID', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ],
        player2Id: [
          {required: true, message: '请输入选手2 ID', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ],
        courtNumber: [
          {required: true, message: '请输入球台编号', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ],
        matchTime: [
          {required: true, message: '请选择比赛时间', trigger: 'change'}
        ]
      }
    };
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/matchSchedule/del?id=' + id).then(res => res.data).then(res => {
        if(res.code === 200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.loadPost();
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      });
    },
    mod(row){
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.form = {...row};
      });
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/matchSchedule/save', this.form).then(res => res.data).then(res => {
        if(res.code === 200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false;
          this.loadPost();
          this.resetForm();
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      });
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/matchSchedule/update', this.form).then(res => res.data).then(res => {
        if(res.code === 200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false;
          this.loadPost();
          this.resetForm();
        } else {
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      });
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form.id){
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleSizeChange(val) {
      this.pageNum = 1;
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadPost();
    },
    resetParam(){
      this.groupType = '';
      this.player1Id = '';
      this.player2Id = '';
      this.loadPost();
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/matchSchedule/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          groupType: this.groupType,
          player1Id: this.player1Id,
          player2Id: this.player2Id,
        }
      }).then(res => res.data).then(res => {
        if(res.code === 200){
          this.tableData = res.data;
          this.total = res.total;
        } else {
          alert('获取数据失败');
        }
      });
    }
  },
  beforeMount() {
    this.loadPost();
  }
};
</script>

<style scoped>

</style>