<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="studentId" placeholder="请输入学员ID" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="groupType" placeholder="请输入组别" suffix-icon="el-icon-search" style="width: 200px; margin-left: 5px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>

    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border>
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="studentId" label="学员ID"></el-table-column>
      <el-table-column prop="groupType" label="组别"></el-table-column>
      <el-table-column prop="registrationFee" label="报名费" width="100"></el-table-column>
      <el-table-column prop="paymentStatus" label="支付状态" width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.paymentStatus === '已支付' ? 'success' : 'danger'"
                  disable-transitions>{{ scope.row.paymentStatus }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="registrationDate" label="报名时间" width="180"></el-table-column>
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
        title="月赛报名信息"
        :visible.sync="centerDialogVisible"
        width="60%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="120px">
        <el-form-item label="学员ID" prop="studentId">
          <el-input v-model="form.studentId"></el-input>
        </el-form-item>
        <el-form-item label="组别" prop="groupType">
          <el-select v-model="form.groupType" placeholder="请选择组别" style="width: 100%;">
            <el-option label="甲组" value="甲"></el-option>
            <el-option label="乙组" value="乙"></el-option>
            <el-option label="丙组" value="丙"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报名费" prop="registrationFee">
          <el-input v-model="form.registrationFee"></el-input>
        </el-form-item>
        <el-form-item label="支付状态" prop="paymentStatus">
          <el-radio-group v-model="form.paymentStatus">
            <el-radio label="已支付"></el-radio>
            <el-radio label="未支付"></el-radio>
          </el-radio-group>
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
  name: "MatchRegistration",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      // 搜索参数
      studentId: '',
      groupType: '',
      centerDialogVisible: false,
      form: {
        id: '',
        studentId: '',
        groupType: '',
        registrationFee: '',
        paymentStatus: '',
        registrationDate: ''
      },
      rules: {
        studentId: [
          {required: true, message: '请输入学员ID', trigger: 'blur'}
        ],
        groupType: [
          {required: true, message: '请选择组别', trigger: 'change'}
        ],
        registrationFee: [
          {required: true, message: '请输入报名费', trigger: 'blur'},
          // 添加数字验证规则
          {pattern: /^\d+(\.\d{1,2})?$/, message: '请输入正确的金额格式', trigger: 'blur'}
        ],
        paymentStatus: [
          {required: true, message: '请选择支付状态', trigger: 'change'}
        ]
      }
    };
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id) {
      this.$axios.get(this.$httpUrl + '/matchRegistration/del?id=' + id).then(res => res.data).then(res => {
        if (res.code === 200) {
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
    mod(row) {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        // 确保数据正确赋值
        this.form = {...row};
      });
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form = {
          id: null,
          studentId: '',
          groupType: '',
          registrationFee: null,
          paymentStatus: '',
          registrationDate: ''
        };
        this.$refs.form.resetFields();
      });
    },
    doSave() {
      this.$axios.post(this.$httpUrl + '/matchRegistration/save', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
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
    doMod() {
      this.$axios.post(this.$httpUrl + '/matchRegistration/update', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
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
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log('表单验证失败!!');
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
    resetParam() {
      this.studentId = '';
      this.groupType = '';
      this.loadPost();
    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/matchRegistration/listPage', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          studentId: this.studentId,
          groupType: this.groupType,
        }
      }).then(res => res.data).then(res => {
        if (res.code === 200) {
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