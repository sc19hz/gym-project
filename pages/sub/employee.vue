<template>
	<view>
		<view style="position: absolute; left: 16%; top: 0; width: 81%; padding: 0 1.5%;">
			<u-row customStyle="margin-top: 10vh;">
				<view style="color: #6e6893; font-size: 40rpx; font-weight: bold;">EMPLOYEES</view>
			</u-row>
			
			<view style="margin-top: 5rpx; border-bottom-style: solid; border-color: #c6c2de; border-width: 3rpx; width: 100%;"></view>
			
			<!-- Requests table -->
			<view style="border-radius: 30rpx; margin-top: 30rpx; box-shadow: 0 0 7px -3rpx #888888;;">
				<u-row customStyle="font-weight: bold; padding: 20rpx 0; color: #6e6893; background-color: #f4f2ff; border-radius: 30rpx 30rpx 0 0;">
					<u-col span="1">
						<view style="text-align: center;">ID</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">NAME</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">POSITION</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">SINCE</view>
					</u-col>
					<u-col span="2" customStyle="text-align: center;">
						<view style="text-align: center;">OPERATION</view>
					</u-col>
				</u-row>
				
				<block v-for="item, index in employees">
					<u-row customStyle="color: #606266; border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; padding: 30rpx 0;">
						<u-col span="1">
							<view style="text-align: center;">{{ item.id }}</view>
						</u-col>
						<u-col span="3">
							<view style="text-align: center;">{{ item.name }}</view>
						</u-col>
						<u-col span="3">
							<view style="text-align: center;">Assistant</view>
						</u-col>
						<u-col span="3">
							<view style="text-align: center;">{{ formatDateTime(item.regisTime) }}</view>
						</u-col>
						<u-col span="2">
							<u-row justify="center">
								<u-icon @click="removeEmployee=index" name="close" color="#606266"></u-icon>
							</u-row>
						</u-col>
					</u-row>
				</block>
				<u-row v-if="employees.length==0"
					justify="center"
					customStyle="font-weight: bold; color: #909399; border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; padding: 30rpx 0;"
				>No Employee...</u-row>
				
				<!-- TODO: create a new venue -->
				<u-row
					@click="showGenCode=true"
					justify="center"
					customStyle="border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; background-color: #f4f2ff; border-radius: 0 0 30rpx 30rpx; padding: 20rpx 0;">
					<u-icon name="plus" color="#6e6893"></u-icon>
					<view style="margin-left: 10rpx; color: #6e6893;">Generate a new invitation code</view>
				</u-row>
			</view>
		</view>
		
		<u-popup :show="removeEmployee!==''" @close="removeEmployee=''" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #f56c6c; font-size: 40rpx; font-weight: bold;">WARNING</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">Are your sure you want to remove this employee?</view>
				
				<u-row>
					<u-button @click="removeEmployee=''" text="Cancel" customStyle="margin-left: 50%; color: #909399; border-radius: 15rpx;"></u-button>
					<u-button @click="delEmployee" text="Confirm" color="#f56c6c" customStyle="margin-left: 5%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<u-popup :show="successInfo!=''" @close="successInfo=''" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #2979ff; font-size: 40rpx; font-weight: bold;">SUCCESS</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">
					{{ successInfo }}
				</view>
				
				<u-row>
					<u-button @click="successInfo=''" text="OK" type="primary" customStyle="margin-top: 40rpx; margin-left: 50%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<u-popup :show="showGenCode" @close="showGenCode=false" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #2979ff; font-size: 40rpx; font-weight: bold;">INFO</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">
					You are going to generate an invitation code. It can be used to register your employees.
				</view>
				
				<u-row>
					<u-button @click="showGenCode=false" text="Cancel" customStyle="margin-left: 50%; color: #909399; border-radius: 15rpx;"></u-button>
					<u-button @click="genCode" text="Gen" type="primary" customStyle="margin-left: 5%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<u-popup :show="newCode!=''" @close="newCode=''" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #2979ff; font-size: 40rpx; font-weight: bold;">SUCCESS</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">
					Your invitation code:
					<text style="color: #6d6d6d; font-size: 34rpx; font-weight: bold; margin-left: 10rpx;">
						{{ newCode }}
					</text>
				</view>
				
				<u-row>
					<u-button @click="newCode=''" text="OK" type="primary" customStyle="margin-top: 40rpx; margin-left: 50%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<!--
		 - Side tool bar
		-->
		<view style="position: fixed; width: 16%; height: 100%; border-right-style: solid; border-color: #e2e2e2; border-width: 3rpx;">
			<u-row customStyle="margin-top: 120rpx;">
				<image
					src="../../static/icon/logo.png"
					mode="aspectFit"
					style="height: 120rpx; width: 120rpx; margin-left: 12%;"
				></image>
				<view>
					<view
						style="color: #6d6d6d; font-weight: bold; font-size: 40rpx; margin-left: 10%;"
					>Redwhale</view>
					
					<view
						style="color: #a6a6a6; margin-left: 10%; white-space: nowrap; font-size: 30rpx;"
					>Growth & Marketing</view>
				</view>
			</u-row>
			
			<u-row @click="gotoVenues" customStyle="margin-top: 25%;">
				<image
					src="../../static/icon/venues.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="font-weight: bold; margin-left: 30rpx; color: #999;">Sports Grounds</view>
			</u-row>
			
			<u-row @click="gotoUserRequest" customStyle="margin-top: 10%;">
				<image
					src="../../static/icon/user-request.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="font-weight: bold; margin-left: 30rpx; color: #999;">User Requests</view>
			</u-row>
			
			<u-row customStyle="margin-top: 10%;">
				<image
					src="../../static/icon/employee-active.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="font-weight: bold; margin-left: 30rpx;">Employees</view>
			</u-row>
			
			<view style="margin: 40% 0 0 15%; border-width: 3rpx; border-color: #e2e2e2; width: 70%; border-bottom-style: solid;"></view>
			
			<u-row @click="showLogout=true" customStyle="margin-top: 3%;">
				<image
					src="../../static/icon/logout.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="margin-top: 60rpx; font-weight: bold; margin-left: 30rpx;">Logout</view>
			</u-row>
		</view>
		
		<u-popup :show="showLogout" @close="showLogout=false" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #2979ff; font-size: 40rpx; font-weight: bold;">INFO</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">Are your sure you want to logout?</view>
				
				<u-row>
					<u-button @click="showLogout=false" text="Cancel" customStyle="margin-left: 50%; color: #909399; border-radius: 15rpx;"></u-button>
					<u-button @click="logout" text="Logout" type="primary" customStyle="margin-left: 5%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				showLogout: false,
				showGenCode: false,
				removeEmployee: '',
				successInfo: '',
				newCode: '',
				
				employees: [ ],
			}
		},
		
		async onLoad() {
			let employees = await uni.$u.http.post("/employee-list");
			for(let i in employees)
			{
				let res = await uni.$u.http.post("/of-user", { }, { params: { userId: employees[i].userId } });
				employees[i].name = res.displayName;
			}
			
			this.employees = employees;
		},
		
		methods: {
			async delEmployee() {
				let res = await uni.$u.http.post(
					"/del-employee",
					{ },
					{
						params: {
							employeeId: this.employees[this.removeEmployee].id
						}
					},
				);
				
				if(res.status == 200)
				{
					this.successInfo = "Successfully removed the employee!";
					this.employees.splice(this.removeEmployee, 1);
				}
				else uni.showToast({
					title: res.message,
					icon:"error",
					duration:3000
				});
				this.removeEmployee = '';
			},
			
			async genCode() {
				let res = await uni.$u.http.post("/gen-invitation-code");
				this.newCode = res.code;
				
				this.showGenCode = false;
			},
			
			gotoVenues() {
				uni.$u.route({
					url: "pages/sub/manage",
					type: "reLaunch"
				});
			},
			
			gotoUserRequest() {
				uni.$u.route({
					url: "pages/sub/user-request",
					type: "reLaunch"
				});
			},
			
			logout() {
				uni.removeStorageSync("token");
				uni.$u.route({
					url: "pages/auth/login",
					type: "reLaunch"
				});
			},
			
			formatDateTime(time) {
				let date = new Date(time);
				let hour = date.getHours();
				let min = date.getMinutes();
				return(
					(date.getMonth() + 1)
					+ "/" + date.getDate()
					+ "/" + date.getFullYear()
					+ "-" + (hour < 10 ? '0' + hour : hour)
					+ ":" + (min < 10 ? '0' + min : min)
				);
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
