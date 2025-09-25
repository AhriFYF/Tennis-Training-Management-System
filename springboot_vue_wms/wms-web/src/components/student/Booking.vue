<template>
  <div class="booking-container">
    <h2>课程预约</h2>
    
    <!-- 教练选择区域 -->
    <div class="coach-selection-section">
      <el-alert
        title="请先选择您的教练，然后查看该教练的可用课程"
        type="info"
        show-icon
        style="margin-bottom: 20px;"
      ></el-alert>
      
      <el-card class="coach-selection-card">
        <div slot="header">
          <span>选择教练</span>
        </div>
        <el-select 
          v-model="selectedCoach" 
          placeholder="请选择您的教练" 
          @change="loadCoachCourses"
          style="width: 100%;"
        >
          <el-option
            v-for="coach in myCoaches"
            :key="coach.userId"
            :label="coach.name"
            :value="coach.userId"
          >
            <span style="float: left">{{ coach.name }}</span>
          </el-option>
        </el-select>
      </el-card>
    </div>

    <!-- 课程列表 -->
    <div class="courses-section" v-if="selectedCoach">
      <el-card class="courses-card">
        <div slot="header">
          <span>{{ selectedCoachName }} 的可用课程</span>
        </div>
        <el-table 
          :data="coachCourses" 
          style="width: 100%" 
          stripe 
          v-loading="loading"
          empty-text="暂无可用课程"
        >
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
                预约
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>

    <!-- 我的预约记录 -->
    <div class="my-bookings-section">
      <el-card class="my-bookings-card">
        <div slot="header">
          <span>我的预约记录</span>
        </div>
        <el-table 
          :data="myBookings" 
          style="width: 100%" 
          stripe
          empty-text="暂无预约记录"
        >
          <el-table-column prop="coachName" label="教练" width="120"></el-table-column>
          <el-table-column prop="courseName" label="课程" width="150"></el-table-column>
          <el-table-column prop="courseStartTime" label="预约时间" width="180">
            <template slot-scope="scope">
              {{ formatDateTime(scope.row.courseStartTime) }}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="120">
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
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>

    <!-- 预约对话框 -->
    <el-dialog
      title="确认预约"
      :visible.sync="bookDialogVisible"
      width="400px"
      :before-close="handleClose"
    >
      <div v-if="selectedCourse">
        <el-alert
          title="预约成功后需等待教练确认"
          type="warning"
          show-icon
          style="margin-bottom: 20px;"
        ></el-alert>
        
        <el-form :model="bookForm" label-width="80px">
          <el-form-item label="课程名称">
            <span>{{ selectedCourse.courseName }}</span>
          </el-form-item>
          <el-form-item label="教练">
            <span>{{ selectedCoachName }}</span>
          </el-form-item>
          <el-form-item label="上课时间">
            <span>{{ formatDateTime(selectedCourse.courseStartTime) }} - {{ formatDateTime(selectedCourse.courseEndTime) }}</span>
          </el-form-item>
          <el-form-item label="费用">
            <span>{{ selectedCourse.coursePrices }}元</span>
          </el-form-item>
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
  name: "Booking",
  data() {
    return {
      myCoaches: [],
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
        case '0': return 'warning'; // 待确认
        case '1': return 'success'; // 已确认
        case '2': return 'info';    // 已取消
        default: return '';
      }
    },
    getStatusText(status) {
      const statusMap = {
        '0': '待确认',
        '1': '已确认',
        '2': '已取消'
      };
      return statusMap[status] || '未知';
    },
    handleClose(done) {
      this.$confirm('确认关闭预约窗口吗？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    // 加载我的教练列表
    loadMyCoaches() {
      if (!this.currentUser) return;
      
      // 调用后端接口获取已确认的教练列表
      this.$axios.get(`${this.$httpUrl}/api/student/coach/my/list/${this.currentUser.id}`)
        .then(res => res.data)
        .then(res => {
          if (res.code === 200) {
            this.myCoaches = res.data;
            // 同时保存到 sessionStorage
            sessionStorage.setItem('myCoaches', JSON.stringify(res.data));
          } else {
            this.$message.error(res.msg || '获取教练列表失败');
          }
        })
        .catch(error => {
          // 如果直接获取失败，尝试从 sessionStorage 获取缓存数据
          const cachedCoaches = sessionStorage.getItem('myCoaches');
          if (cachedCoaches) {
            this.myCoaches = JSON.parse(cachedCoaches);
            this.$message.warning('获取教练列表失败，使用缓存数据');
          } else {
            this.$message.error('获取教练列表失败');
          }
          console.error(error);
        });
    },
    // 加载教练的课程
    loadCoachCourses(coachUserId) {
      this.loading = true;
      
      // 找到选中的教练名称
      const coach = this.myCoaches.find(c => c.userId === coachUserId);
      this.selectedCoachName = coach ? coach.name : '';
      
      // 调用后端接口获取该教练的课程
      this.$axios.get(`${this.$httpUrl}/api/student/booking/courses/${coachUserId}`)
        .then(res => res.data)
        .then(res => {
          if (res.code === 200) {
            this.coachCourses = res.data;
          } else {
            this.$message.error(res.msg || '获取课程列表失败');
            this.coachCourses = []; // 确保在失败时清空课程列表
          }
          this.loading = false;
        })
        .catch(error => {
          this.$message.error('获取课程列表失败');
          console.error(error);
          this.coachCourses = []; // 确保在失败时清空课程列表
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
          this.$message.error('加载预约记录失败');
          console.error(error);
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
      if (!this.selectedCourse || !this.currentUser || !this.selectedCoach) return;

      const bookingData = {
        studentUsers: this.currentUser.id,
        sourceId: this.selectedCourse.courseInformationId,
        coursePrices: this.selectedCourse.coursePrices,
        // 其他字段将在后端服务中设置
      };

      this.$axios.post(`${this.$httpUrl}/api/student/booking/select`, bookingData)
        .then(res => res.data)
        .then(res => {
          if (res.code === 200) {
            this.$message.success('预约申请已提交，等待教练确认');
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
      this.$confirm('确定要取消此预约吗? 预约取消后将无法恢复。', '提示', {
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
      this.loadMyCoaches();
      this.loadMyBookings();
    }
  }
};
</script>

<style scoped>
.booking-container {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: calc(100vh - 80px);
}

.coach-selection-section {
  margin-bottom: 30px;
}

.coach-selection-card, .courses-card, .my-bookings-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.courses-section {
  margin-bottom: 30px;
}

.my-bookings-section {
  margin-bottom: 30px;
}
</style>