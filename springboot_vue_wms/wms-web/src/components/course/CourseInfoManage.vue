<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="courseName" placeholder="请输入课程名称" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="courseTypes" placeholder="请输入课程类型" suffix-icon="el-icon-search" style="width: 200px; margin-left: 5px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>
    </div>

    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border>
      <el-table-column prop="courseInformationId" label="ID" width="60"></el-table-column>
      <el-table-column prop="courseName" label="课程名称"></el-table-column>
      <el-table-column prop="courseTypes" label="课程类型"></el-table-column>
      <el-table-column prop="coachName" label="教练姓名"></el-table-column>
      <el-table-column prop="coursePrices" label="课程价格" width="100"></el-table-column>
      <el-table-column prop="courseHours" label="课时数" width="100"></el-table-column>
      <el-table-column prop="durationOfClassHours" label="课时时长" width="120"></el-table-column>
      <el-table-column prop="status" label="状态" width="80"></el-table-column>
      <el-table-column prop="operate" label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？"
              @confirm="del(scope.row.courseInformationId)"
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
        title="课程信息"
        :visible.sync="centerDialogVisible"
        width="60%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="120px">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName"></el-input>
        </el-form-item>
        <el-form-item label="课程类型" prop="courseTypes">
          <el-input v-model="form.courseTypes"></el-input>
        </el-form-item>
        <el-form-item label="教练用户ID" prop="coachUser">
          <el-input v-model="form.coachUser"></el-input>
        </el-form-item>
        <el-form-item label="教练姓名" prop="coachName">
          <el-input v-model="form.coachName"></el-input>
        </el-form-item>
        <el-form-item label="课时数" prop="courseHours">
          <el-input v-model="form.courseHours"></el-input>
        </el-form-item>
        <el-form-item label="课时时长" prop="durationOfClassHours">
          <el-input v-model="form.durationOfClassHours"></el-input>
        </el-form-item>
        <el-form-item label="课程价格" prop="coursePrices">
          <el-input v-model="form.coursePrices"></el-input>
        </el-form-item>
        <el-form-item label="课程介绍" prop="courseIntroduction">
          <el-input type="textarea" v-model="form.courseIntroduction"></el-input>
        </el-form-item>
        form-item
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
  name: "CourseInfoManage",
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
    let checkCourseId = (rule, value, callback) => {
      if(this.form.courseInformationId) {
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/course/checkCourseId?id="+this.form.courseInformationId).then(res=>res.data).then(res=>{
        if(res.code != 200){
          callback()
        } else {
          callback(new Error('课程ID已存在'));
        }
      })
    };

    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      // 搜索参数
      courseName: '',
      courseTypes: '',
      centerDialogVisible: false,
      form: {
        courseInformationId: '',
        courseName: '',
        courseTypes: '',
        coachUser: '',
        coachName: '',
        courseHours: '',
        durationOfClassHours: '',
        coursePrices: '',
        courseImages: '',
        courseIntroduction: '',
        hits: '',
        praiseLen: '',
        collectLen: '',
        commentLen: '',
        studentCourseSelectionLimitTimes: '',
        status: '',
        courseStartTime: '',
        courseEndTime: ''
      },
      rules: {
        courseName: [
          {required: true, message: '请输入课程名称', trigger: 'blur'}
        ],
        courseTypes: [
          {required: true, message: '请输入课程类型', trigger: 'blur'}
        ],
        coachName: [
          {required: true, message: '请输入教练姓名', trigger: 'blur'}
        ],
        coursePrices: [
          {required: true, message: '请输入课程价格', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ],
        courseHours: [
          {required: true, message: '请输入课时数', trigger: 'blur'},
          {validator: checkNumber, trigger: 'blur'}
        ],
        courseIntroduction: [
          {required: true, message: '请输入课程介绍', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/course/del?id=' + id).then(res => res.data).then(res => {
        if(res.code == 200){
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
        this.form = {...row}; // 使用扩展运算符方便地将行数据赋值给表单
      });
    },
    add(){
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        this.resetForm();
      });
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/course/save', this.form).then(res => res.data).then(res => {
        if(res.code == 200){
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
      this.$axios.post(this.$httpUrl+'/course/update', this.form).then(res => res.data).then(res => {
        if(res.code == 200){
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
          if(this.form.courseInformationId){
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
      this.courseName = '';
      this.courseTypes = '';
      this.loadPost();
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/course/listPageC1', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          courseName: this.courseName,
          courseTypes: this.courseTypes
        }
      }).then(res => res.data).then(res => {
        if(res.code == 200){
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