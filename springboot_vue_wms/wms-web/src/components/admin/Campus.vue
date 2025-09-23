<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入校区名称" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-input v-model="contactPerson" placeholder="请输入联系人" suffix-icon="el-icon-search" style="width: 200px; margin-left: 5px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>
    </div>

    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border>
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="name" label="校区名称"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="contactPerson" label="联系人" width="120"></el-table-column>
      <el-table-column prop="contactPhone" label="联系电话" width="150"></el-table-column>
      <el-table-column prop="isCenter" label="是否为中心校区" width="150">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.isCenter ? 'primary' : 'success'"
              disable-transitions>{{scope.row.isCenter ? '是' : '否'}}</el-tag>
        </template>
      </el-table-column>
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
        title="校区信息"
        :visible.sync="centerDialogVisible"
        width="60%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="120px">
        <el-form-item label="校区名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone"></el-input>
        </el-form-item>
        <el-form-item label="联系邮箱" prop="contactEmail">
          <el-input v-model="form.contactEmail"></el-input>
        </el-form-item>
        <el-form-item label="是否为中心校区">
          <el-switch v-model="form.isCenter"
                     :active-value="1"
                     :inactive-value="0"
                     active-color="#13ce66"
                     inactive-color="#ff4949">
          </el-switch>
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
  name: "Campus",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      // 搜索参数
      name: '',
      contactPerson: '',
      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        address: '',
        contactPerson: '',
        contactPhone: '',
        contactEmail: '',
        parentCampus: null,
        isCenter: false
      },
      rules: {
        name: [
          {required: true, message: '请输入校区名称', trigger: 'blur'}
        ],
        address: [
          {required: true, message: '请输入地址', trigger: 'blur'}
        ],
        contactPerson: [
          {required: true, message: '请输入联系人', trigger: 'blur'}
        ],
        contactPhone: [
          {required: true, message: '请输入联系电话', trigger: 'blur'},
          {pattern: /^1[1|3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ],
        contactEmail: [
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id) {
      this.$axios.get(this.$httpUrl + '/campus/del?id=' + id).then(res => res.data).then(res => {
        if (res.code == 200) {
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
        this.form = {...row, isCenter: row.isCenter};
      });
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(()=>{
        // 使用模板创建一个全新的表单对象
        this.form = { ...this.formTemplate };
        // 重置表单验证状态
        this.$refs.form.resetFields();
      });
    },
    doSave() {
      this.$axios.post(this.$httpUrl + '/campus/save', this.form).then(res => res.data).then(res => {
        if (res.code == 200) {
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
      this.$axios.post(this.$httpUrl + '/campus/update', this.form).then(res => res.data).then(res => {
        if (res.code == 200) {
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
    resetParam() {
      this.name = '';
      this.contactPerson = '';
      this.loadPost();
    },
    loadPost() {
      this.$axios.post(this.$httpUrl + '/campus/listPageC1', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          contactPerson: this.contactPerson,
        }
      }).then(res => res.data).then(res => {
        if (res.code == 200) {
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
}
</script>

<style scoped>

</style>