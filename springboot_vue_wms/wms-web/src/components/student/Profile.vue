<template>
  <div class="profile-page">
    <el-card class="profile-card">
      <div slot="header" class="card-header">
        <span>个人信息</span>
        <el-button
            type="primary"
            size="small"
            @click="isEditing = true"
            v-if="!isEditing"
        >
          编辑资料
        </el-button>
        <el-button
            type="success"
            size="small"
            @click="saveProfile"
            v-else
        >
          保存修改
        </el-button>
      </div>

      <el-form
          ref="profileForm"
          :model="profileData"
          :rules="rules"
          label-width="120px"
          label-position="left"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input
                  v-model="profileData.name"
                  :disabled="!isEditing"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group
                  v-model="profileData.gender"
                  :disabled="!isEditing"
              >
                <el-radio label="male">男</el-radio>
                <el-radio label="female">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number
                  v-model="profileData.age"
                  :disabled="!isEditing"
                  :min="6"
                  :max="100"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input
                  v-model="profileData.phone"
                  :disabled="!isEditing"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="地址" prop="address">
              <el-input
                  type="textarea"
                  v-model="profileData.address"
                  :disabled="!isEditing"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row v-if="isEditing">
          <el-col :span="24">
            <el-button
                type="danger"
                size="small"
                @click="cancelEdit"
                style="float: right"
            >
              取消修改
            </el-button>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Profile",
  data() {
    return {
      isEditing: false,
      profileData: {
        name: "",
        gender: "male",
        age: 0,
        phone: "",
        address: ""
      },
      rules: {
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { min: 2, max: 10, message: "长度在 2 到 10 个字符", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "请输入电话号码", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              const phoneReg = /^1[3-9]\d{9}$/;
              if (!phoneReg.test(value)) {
                callback(new Error("请输入正确的手机号码"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.loadProfile();
  },
  methods: {
    async loadProfile() {
      try {
        const response = await this.$http.get("/api/user/profile");
        this.profileData = response.data;
      } catch (error) {
        this.$message.error("加载个人信息失败");
      }
    },
    async saveProfile() {
      try {
        await this.$refs.profileForm.validate();
        await this.$http.put("/api/user/profile", this.profileData);
        this.isEditing = false;
        this.$message.success("资料保存成功");
      } catch (error) {
        this.$message.error("保存失败：" + error.message);
      }
    },
    cancelEdit() {
      this.isEditing = false;
      this.loadProfile();
    }
  }
};
</script>

<style scoped>
.profile-page {
  padding: 20px;
}

.profile-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>