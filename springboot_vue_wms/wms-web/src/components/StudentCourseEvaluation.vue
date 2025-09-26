<template>
  <div class="course-evaluation-container">
    <h2>课程评价</h2>
    
    <!-- 已选课程列表 -->
    <el-card class="course-list-card">
      <div slot="header" class="clearfix">
        <span>已选课程</span>
      </div>
      
      <el-table :data="selectedCourses" style="width: 100%" v-loading="loading">
        <el-table-column prop="courseName" label="课程名称" width="150"></el-table-column>
        <el-table-column prop="coachName" label="教练" width="100"></el-table-column>
        <el-table-column prop="createTime" label="预约时间" width="120">
          <template slot-scope="scope">
            {{ scope.row.createTime | formatDate }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '1' ? 'success' : 'warning'">
              {{ scope.row.status === '1' ? '已确认' : '未确认' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button 
              size="mini" 
              type="primary" 
              @click="openEvaluationDialog(scope.row)"
              :disabled="isEvaluated(scope.row) || scope.row.status !== '1'">
              {{ isEvaluated(scope.row) ? '已评价' : '评价' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 评价对话框 -->
    <el-dialog title="课程评价" :visible.sync="evaluationDialogVisible" width="50%" :before-close="handleClose">
      <el-form :model="evaluationForm" :rules="evaluationRules" ref="evaluationForm" label-width="100px">
        <el-form-item label="课程名称">
          <el-input v-model="evaluationForm.courseName" disabled></el-input>
        </el-form-item>
        <el-form-item label="教练">
          <el-input v-model="evaluationForm.coachName" disabled></el-input>
        </el-form-item>
        <el-form-item label="预约时间">
          <el-input v-model="evaluationForm.createTime" disabled></el-input>
        </el-form-item>
        <el-form-item label="评价内容" prop="evaluationContent">
          <el-input 
            type="textarea" 
            v-model="evaluationForm.evaluationContent" 
            placeholder="请输入您的评价内容"
            :rows="4"
            maxlength="500"
            show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="评分" prop="rating">
          <el-rate v-model="evaluationForm.rating" :max="5" show-score></el-rate>
        </el-form-item>
      </el-form>
      
      <span slot="footer" class="dialog-footer">
        <el-button @click="evaluationDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEvaluation">提 交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "StudentCourseEvaluation",
  data() {
    return {
      loading: false,
      selectedCourses: [],
      evaluationDialogVisible: false,
      evaluationForm: {
        teachingEvaluationId: null,
        courseName: '',
        coachName: '',
        createTime: '',
        evaluationContent: '',
        rating: 0
      },
      evaluationRules: {
        evaluationContent: [
          { required: true, message: '请输入评价内容', trigger: 'blur' },
          { min: 10, max: 500, message: '评价内容长度在10到500个字符', trigger: 'blur' }
        ],
        rating: [
          { required: true, message: '请评分', trigger: 'change' }
        ]
      },
      // 已评价的课程ID列表
      evaluatedCourses: []
    };
  },
  filters: {
    formatDate(date) {
      if (!date) return '';
      const d = new Date(date);
      return `${d.getFullYear()}-${d.getMonth() + 1}-${d.getDate()}`;
    }
  },
  mounted() {
    this.loadSelectedCourses();
  },
  methods: {
    // 加载已选课程
    loadSelectedCourses() {
      this.loading = true;
      // 调用后端接口获取已选课程
      this.$axios.get(`/api/student/course/selection/${this.currentUser.id}`)
        .then(response => {
          if (response.data.code === 200) {
            this.selectedCourses = response.data.data;
          } else {
            this.$message.error('获取课程列表失败: ' + (response.data.msg || '未知错误'));
          }
          this.loading = false;
        })
        .catch(error => {
          console.error('获取课程列表错误:', error);
          this.$message.error('获取课程列表失败，请稍后重试');
          this.loading = false;
        });
    },
    
    // 打开评价对话框
    openEvaluationDialog(course) {
      this.evaluationForm = {
        teachingEvaluationId: course.studentCourseSelectionId,
        courseName: course.courseName,
        coachName: course.coachName,
        createTime: course.createTime,
        evaluationContent: '',
        rating: 0
      };
      this.evaluationDialogVisible = true;
    },
    
    // 检查课程是否已评价
    isEvaluated() {
      // 这里应该检查该课程是否已评价
      // 暂时返回false，表示都未评价
      return false;
    },
    
    // 提交评价
    submitEvaluation() {
      this.$refs.evaluationForm.validate((valid) => {
        if (valid) {
          // 调用后端接口提交评价
          const evaluationData = {
            ...this.evaluationForm,
            evaluationContent: this.evaluationForm.evaluationContent,
            sourceUserId: this.currentUser.id,
            campusId: this.currentUser.campusId,
            studentUsers: this.currentUser.id
          };
          
          this.$axios.post('/evaluation/save', evaluationData)
            .then(response => {
              if (response.data.code === 200) {
                this.$message.success('评价提交成功');
                // 更新已评价课程列表
                this.evaluatedCourses.push(this.evaluationForm.teachingEvaluationId);
                this.evaluationDialogVisible = false;
                // 重新加载课程列表
                this.loadSelectedCourses();
              } else {
                this.$message.error(response.data.msg || '评价提交失败');
              }
            })
            .catch(error => {
              console.error('提交评价错误:', error);
              this.$message.error('评价提交失败，请稍后重试');
            });
        } else {
          this.$message.error('请正确填写评价信息');
          return false;
        }
      });
    },
    
    // 关闭对话框前的处理
    handleClose(done) {
      this.$confirm('确认关闭评价窗口吗？')
          // eslint-disable-next-line no-unused-vars
        .then(_ => {
          done();
        })
          // eslint-disable-next-line no-unused-vars
        .catch(_ => {});
    }
  },
  computed: {
    currentUser() {
      return JSON.parse(sessionStorage.getItem("CurUser") || "{}");
    }
  }
};
</script>

<style scoped>
.course-evaluation-container {
  padding: 20px;
}

.course-list-card {
  margin-bottom: 20px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>