<template>
  <div>
    <h2>月赛赛程</h2>
    <el-alert
        title="月赛赛程安排"
        type="info"
        show-icon
        style="margin-bottom: 20px;">
    </el-alert>

    <el-tabs v-model="activeGroup" @tab-click="handleGroupChange">
      <el-tab-pane label="甲组" name="甲"></el-tab-pane>
      <el-tab-pane label="乙组" name="乙"></el-tab-pane>
      <el-tab-pane label="丙组" name="丙"></el-tab-pane>
    </el-tabs>

    <el-card class="schedule-card">
      <div slot="header" class="clearfix">
        <span>{{ activeGroup }}组赛程</span>
      </div>
      <el-table :data="groupSchedules" style="width: 100%" stripe>
        <el-table-column prop="matchRound" label="轮次" width="80"></el-table-column>
        <el-table-column prop="player1Id" label="选手1 ID" width="120"></el-table-column>
        <el-table-column prop="player2Id" label="选手2 ID" width="120"></el-table-column>
        <el-table-column prop="courtNumber" label="球台编号" width="120"></el-table-column>
        <el-table-column prop="matchTime" label="比赛时间" width="200"></el-table-column>
      </el-table>
      <div v-if="groupSchedules.length === 0" style="text-align: center; padding: 20px;">
        <el-alert
            title="暂无赛程安排"
            type="info"
            show-icon>
        </el-alert>
      </div>
    </el-card>

    <el-card class="my-schedule-card" style="margin-top: 20px;">
      <div slot="header" class="clearfix">
        <span>我的比赛安排</span>
      </div>
      <el-table :data="mySchedules" style="width: 100%" stripe>
        <el-table-column prop="groupType" label="组别" width="80"></el-table-column>
        <el-table-column prop="matchRound" label="轮次" width="80"></el-table-column>
        <el-table-column label="对手" width="120">
          <template slot-scope="scope">
            {{ scope.row.player1Id == currentUserId ? scope.row.player2Id : scope.row.player1Id }}
          </template>
        </el-table-column>
        <el-table-column prop="courtNumber" label="球台编号" width="120"></el-table-column>
        <el-table-column prop="matchTime" label="比赛时间" width="200"></el-table-column>
      </el-table>
      <div v-if="mySchedules.length === 0" style="text-align: center; padding: 20px;">
        <el-alert
            title="您暂无比赛安排"
            type="info"
            show-icon>
        </el-alert>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "MonthlyMatchSchedule",
  data() {
    return {
      activeGroup: '甲',
      groupSchedules: [],
      mySchedules: [],
      currentUserId: null
    };
  },
  methods: {
    handleGroupChange() {
      this.loadGroupSchedules();
    },
    loadGroupSchedules() {
      this.$axios.get(this.$httpUrl + '/matchSchedule/byGroup/' + this.activeGroup)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.groupSchedules = res.data;
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          });
    },
    loadMySchedules() {
      // 从session中获取当前用户ID
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.currentUserId = user.id;
      }
      
      this.$axios.get(this.$httpUrl + '/matchSchedule/byPlayer')
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.mySchedules = res.data;
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          });
    }
  },
  mounted() {
    this.loadGroupSchedules();
    this.loadMySchedules();
  },
  watch: {
    activeGroup() {
      this.loadGroupSchedules();
    }
  }
};
</script>

<style scoped>
.schedule-card, .my-schedule-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>