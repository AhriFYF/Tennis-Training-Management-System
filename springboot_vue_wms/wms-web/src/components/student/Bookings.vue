<template>
  <div class="bookings-container">
    <h2>课程预约</h2>

    <!-- 教练选择区域 -->
    <div class="coach-selection">
      <el-select v-model="selectedCoach" placeholder="请选择教练" @change="loadCoachCourses">
        <el-option
            v-for="coach in availableCoaches"
            :key="coach.coachId"
            :label="coach.name"
            :value="coach.coachId"
        >
          <span style="float: left">{{ coach.name }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">
            {{ coach.level | levelText }} - {{ coach.hourlyRate }}元/小时
          </span>
        </el-option>
      </el-select>
    </div>

    <!-- 课程列表 -->
    <div class="courses-container" v-if="selectedCoach">
      <h3>{{ selectedCoachName }}的可用课程</h3>
      <el-table :data="coachCourses" style="width: 100%" stripe v-loading="loading">
        <el-table-column prop="courseName" label="课程名称" width="150"></el-table-column>
        <el-table-column prop="courseTypes" label="课程类型" width="120"></el-table-column>
        <el-table-column prop="courseHours" label="课时(小时)" width="100"></el-table-column>
        <el-table-column prop="coursePrices" label="费用(元)" width="100">
          <template slot-scope="scope">
            {{ scope.row.coursePrices.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="courseStartTime" label="开始时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.courseStartTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="courseEndTime" label="结束时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.courseEndTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="primary"
                @click="handleBook(scope.row)"
                :disabled="scope.row.status !== '0'"
            >
              {{ scope.row.status === '0' ? '预约' : '已预约' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 我的预约记录 -->
    <div class="my-bookings">
      <h3>我的预约记录</h3>
      <el-table :data="myBookings" style="width: 100%" stripe>
        <el-table-column prop="coachName" label="教练" width="120"></el-table-column>
        <el-table-column prop="courseName" label="课程" width="150"></el-table-column>
        <el-table-column prop="scheduleDate" label="预约时间" width="180">
          <template slot-scope="scope">
            {{ formatDateTime(scope.row.scheduleDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="schedulingPeriod" label="时间段" width="100"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
                v-if="scope.row.status === '0'"
                size="mini"
                type="danger"
                @click="handleCancel(scope.row)"
            >
              取消预约
            </el-button>
            <span v-else>无法取消</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 预约对话框 -->
    <el-dialog
        title="确认预约"
        :visible.sync="bookDialogVisible"
        width="30%">
      <div v-if="selectedCourse">
        <p><strong>课程：</strong>{{ selectedCourse.courseName }}</p>
        <p><strong>教练：</strong>{{ selectedCoachName }}</p>
        <p><strong>时间：</strong>{{ formatDateTime(selectedCourse.courseStartTime) }} - {{ formatDateTime(selectedCourse.courseEndTime) }}</p>
        <p><strong>费用：</strong>{{ selectedCourse.coursePrices }}元</p>

        <el-form :model="bookForm" label-width="80px">
          <el-form-item label="备注">
            <el-input
                type="textarea"
                :rows="3"
                placeholder="请输入备注信息"
                v-model="bookForm.notes">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="bookDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmBook">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Bookings",
  filters: {
    levelText(level) {
      const levelMap = {
        1: '初级',
        2: '中级',
        3: '高级'
      };
      return levelMap[level] || '未知';
    }
  },
  data() {
    return {
      availableCoaches: [],
      selectedCoach: null,
      selectedCoachName: '',
      coachCourses: [],
      myBookings: [],
      loading: false,
      bookDialogVisible: false,
      selectedCourse: null,
      bookForm: {
        notes: ''
      },
      currentUser: null
    };
  },
  methods: {
    formatDateTime(dateTime) {
      if (!dateTime) return '';
      return new Date(dateTime).toLocaleString('zh-CN');
    },
    getStatusTagType(status) {
      switch(status) {
        case '1': return 'success'; // 已确认
        case '0': return 'warning'; // 待确认
        case '2': return 'info';    // 已取消
        case '3': return '';        // 已完成
        default: return '';
      }
    },
    getStatusText(status) {
      const statusMap = {
        '0': '待确认',
        '1': '已确认',
        '2': '已取消',
        '3': '已完成'
      };
      return statusMap[status] || '未知';
    },
    // 加载已确认的教练列表
    loadAvailableCoaches() {
      this.$axios.get(`${this.$httpUrl}/api/student/coach/my/list/${this.currentUser.id}`)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.availableCoaches = res.data;
            }
          })
          .catch(error => {
            console.error('加载教练列表失败', error);
          });
    },
    // 加载教练的课程
    loadCoachCourses(coachId) {
      this.loading = true;
      const coach = this.availableCoaches.find(c => c.coachId === coachId);
      this.selectedCoachName = coach ? coach.name : '';

      this.$axios.get(`${this.$httpUrl}/api/student/course/coach/${coachId}`)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.coachCourses = res.data;
            }
            this.loading = false;
          })
          .catch(() => {
            this.loading = false;
          });
    },
    // 加载我的预约记录
    loadMyBookings() {
      if (!this.currentUser) return;

      this.$axios.get(`${this.$httpUrl}/api/student/course/selection/${this.currentUser.id}`)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.myBookings = res.data;
            }
          })
          .catch(error => {
            console.error('加载预约记录失败', error);
          });
    },
    // 处理预约
    handleBook(course) {
      this.selectedCourse = course;
      this.bookForm.notes = '';
      this.bookDialogVisible = true;
    },
    // 确认预约
    confirmBook() {
      if (!this.selectedCourse || !this.currentUser) return;

      const bookingData = {
        studentId: this.currentUser.id,
        courseInformationId: this.selectedCourse.courseInformationId,
        notes: this.bookForm.notes
      };

      this.$axios.post(`${this.$httpUrl}/api/student/course/selection/create`, bookingData)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.$message.success('预约成功，等待教练确认');
              this.bookDialogVisible = false;
              // 刷新数据
              this.loadCoachCourses(this.selectedCoach);
              this.loadMyBookings();
            } else {
              this.$message.error(res.msg || '预约失败');
            }
          })
          .catch(error => {
            this.$message.error('预约失败');
            console.error(error);
          });
    },
    // 处理取消预约
    handleCancel(booking) {
      this.$confirm('确定要取消此预约吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`${this.$httpUrl}/api/student/course/selection/cancel/${booking.studentCourseSelectionId}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.$message.success('取消预约成功');
                this.loadMyBookings();
              } else {
                this.$message.error(res.msg || '取消预约失败');
              }
            })
            .catch(error => {
              this.$message.error('取消预约失败');
              console.error(error);
            });
      });
    }
  },
  mounted() {
    const user = JSON.parse(sessionStorage.getItem('CurUser'));
    if (user && user.id) {
      this.currentUser = user;
      this.loadAvailableCoaches();
      this.loadMyBookings();
    }
  }
};
</script>

<style scoped>
.bookings-container {
  padding: 20px;
}
.coach-selection {
  margin-bottom: 20px;
}
.courses-container, .my-bookings {
  margin-bottom: 30px;
}
</style>