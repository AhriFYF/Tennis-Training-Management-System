<!-- CoachSearch.vue 教练查询组件 -->
<template>
  <div class="coach-search-container">
    <h2>教练查询</h2>

    <el-card class="search-card">
      <div slot="header" class="clearfix">
        <span>查询条件</span>
      </div>
      <el-form :model="searchForm" label-width="80px">
        <el-form-item label="姓名">
          <el-input v-model="searchForm.name" placeholder="请输入教练姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="searchForm.gender" placeholder="请选择性别">
            <el-option label="男" value="M"></el-option>
            <el-option label="女" value="F"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number v-model="searchForm.age" :min="18" :max="60"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchCoaches">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="info" @click="showAllCoaches">浏览所有教练</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="results-card" style="margin-top: 20px;">
      <div slot="header" class="clearfix">
        <span>查询结果</span>
      </div>
      <div v-if="coaches.length === 0" class="empty-tip">
        <el-alert title="未找到符合条件的教练" type="info" show-icon></el-alert>
      </div>

      <el-row :gutter="20">
        <el-col :span="8" v-for="coach in coaches" :key="coach.coachId" style="margin-bottom: 20px;">
          <el-card class="coach-card">
            <div class="coach-header">
              <el-avatar :size="80" :src="coach.photoUrl" v-if="coach.photoUrl"></el-avatar>
              <el-avatar :size="80" icon="el-icon-user-solid" v-else></el-avatar>
              <div class="coach-info">
                <h3>{{ coach.name }}</h3>
                <p>{{ getLevelText(coach.level) }}教练</p>
              </div>
            </div>

            <div class="coach-details">
              <p><strong>性别:</strong> {{ coach.gender === 'M' ? '男' : '女' }}</p>
              <p><strong>年龄:</strong> {{ coach.age }}</p>
              <p><strong>课时费:</strong> {{ coach.hourlyRate }}元/小时</p>
              <p><strong>校区:</strong> {{ coach.campusName }}</p>
            </div>

            <div class="achievements">
              <h4>比赛成绩:</h4>
              <p>{{ coach.achievements || '暂无成绩信息' }}</p>
            </div>

            <el-button
                type="primary"
                @click="selectCoach(coach)"
                :disabled="hasSelectedCoach(coach.coachId)"
            >
              {{ hasSelectedCoach(coach.coachId) ? '已选择' : '选择教练' }}
            </el-button>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CoachSearch",
  data() {
    return {
      searchForm: {
        name: '',
        gender: '',
        age: null
      },
      coaches: [],
      selectedCoaches: []
    };
  },
  methods: {
    getLevelText(level) {
      switch(level) {
        case 1: return '初级';
        case 2: return '中级';
        case 3: return '高级';
        default: return '初级';
      }
    },
    searchCoaches() {
      this.$axios.post(`${this.$httpUrl}/coach/search`, this.searchForm)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.coaches = res.data;
            }
          });
    },
    resetSearch() {
      this.searchForm = {
        name: '',
        gender: '',
        age: null
      };
      this.coaches = [];
    },
    showAllCoaches() {
      this.$axios.get(`${this.$httpUrl}/coach/all`)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.coaches = res.data;
            }
          });
    },
    selectCoach(coach) {
      if (this.selectedCoaches.length >= 2) {
        this.$message.warning('您最多只能选择两位教练');
        return;
      }

      this.$axios.post(`${this.$httpUrl}/coach/select`, { coachId: coach.coachId })
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.$message.success('申请已提交，等待教练确认');
              this.selectedCoaches.push(coach.coachId);
            } else {
              this.$message.error(res.msg);
            }
          });
    },
    hasSelectedCoach(coachId) {
      return this.selectedCoaches.includes(coachId);
    },
    loadSelectedCoaches() {
      const user = JSON.parse(sessionStorage.getItem('CurUser'));
      if (user && user.id) {
        this.$axios.get(`${this.$httpUrl}/coach/selected/${user.id}`)
            .then(res => res.data)
            .then(res => {
              if (res.code === 200) {
                this.selectedCoaches = res.data.map(c => c.coachId);
              }
            });
      }
    }
  },
  mounted() {
    this.loadSelectedCoaches();
  }
};
</script>

<style scoped>
.coach-search-container {
  padding: 20px;
}

.coach-card {
  height: 100%;
}

.coach-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.coach-info {
  margin-left: 15px;
}

.coach-info h3 {
  margin: 0;
}

.coach-details p {
  margin: 5px 0;
}

.achievements {
  margin: 15px 0;
  border-top: 1px solid #eee;
  padding-top: 10px;
}

.achievements h4 {
  margin: 0 0 10px 0;
}

.empty-tip {
  text-align: center;
  padding: 20px;
}
</style>