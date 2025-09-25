<template>
  <div class="bookings-container">
    <h2>我的课程预约</h2>
    <el-alert
        title="每月最多取消3次预约，取消需对方确认"
        type="info"
        show-icon
        style="margin-bottom: 20px;"
    ></el-alert>

    <el-table :data="bookings" style="width: 100%" stripe v-loading="loading">
      <el-table-column prop="coachName" label="教练" width="120"></el-table-column>
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
      <el-table-column prop="courtNumber" label="球台" width="100"></el-table-column>
      <el-table-column prop="coursePrices" label="费用(元)" width="100">
        <template slot-scope="scope">
          {{ scope.row.coursePrices.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          <el-tag :type="getStatusTagType(scope.row.status)">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button
              v-if="canCancel(scope.row)"
              size="mini"
              type="danger"
              @click="handleCancel(scope.row)"
          >
            取消预约 (剩余{{ remainingCancels }}次)
          </el-button>
          <el-button
              v-else-if="scope.row.status === '已取消'"
              size="mini"
              type="info"
              disabled
          >
            已取消
          </el-button>
          <span v-else-if="!canCancel(scope.row) && scope.row.status === '已确认'">
            无法取消
          </span>
        </template>
      </el-table-column>
    </el-table>

    <div v-if="bookings.length === 0 && !loading" class="empty-tip">
      <el-alert title="暂无预约记录" type="info" show-icon></el-alert>
    </div>

    <!-- 取消预约对话框 -->
    <el-dialog
        title="取消预约"
        :visible.sync="cancelDialogVisible"
        width="30%">
      <el-form :model="cancelForm">
        <el-form-item label="取消原因">
          <el-input
              type="textarea"
              :rows="3"
              placeholder="请输入取消原因"
              v-model="cancelForm.reason">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmCancel">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Bookings",
  data() {
    return {
      bookings: [],
      remainingCancels: 3,
      currentUserId: null,
      loading: false,
      cancelDialogVisible: false,
      cancelForm: {
        bookingId: null,
        reason: ''
      }
    };
  },
  methods: {
    formatDateTime(dateTime) {
      if (!dateTime) return '';
      return new Date(dateTime).toLocaleString('zh-CN');
    },
    getStatusTagType(status) {
      switch(status) {
        case '已确认': return 'success';
        case '待确认': return 'warning';
        case '已取消': return 'info';
        default: return '';
      }
    },
    canCancel(booking) {
      if (booking.status !== '已确认') {
        return false;
      }

      const now = new Date();
      const startTime = new Date(booking.courseStartTime);
      const hoursDiff = (startTime - now) / (1000 * 60 * 60);

      return hoursDiff > 24 && this.remainingCancels > 0;
    },
    handleCancel(booking) {
      this.cancelForm.bookingId = booking.id;
      this.cancelForm.reason = '';
      this.cancelDialogVisible = true;
    },
    confirmCancel() {
      if (!this.cancelForm.reason) {
        this.$message.error('请填写取消原因');
        return;
      }

      this.$axios.post(`${this.$httpUrl}/booking/cancel/${this.cancelForm.bookingId}`, {
        reason: this.cancelForm.reason
      }).then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.$message.success(res.msg);
              this.cancelDialogVisible = false;
              this.loadBookings();
              this.loadCancellationInfo();
            } else {
              this.$message.error(res.msg);
            }
          }).catch(error => {
        this.$message.error('取消预约失败');
        console.error(error);
      });
    },
    loadBookings() {
      this.loading = true;
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.currentUserId = user.id;
        this.$axios.get(`${this.$httpUrl}/booking/student/${user.id}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.bookings = res.data;
              }
              this.loading = false;
            }).catch(() => {
          this.loading = false;
        });
      } else {
        this.loading = false;
      }
    },
    loadCancellationInfo() {
      this.$axios.get(`${this.$httpUrl}/booking/cancellationCount`)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.remainingCancels = res.data.remaining;
            }
          });
    }
  },
  mounted() {
    this.loadBookings();
    this.loadCancellationInfo();
  }
};
</script>

<style scoped>
.bookings-container {
  padding: 20px;
}
.empty-tip {
  margin-top: 20px;
  text-align: center;
}
</style>