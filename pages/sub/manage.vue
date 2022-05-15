<template>
	<view>
		<!--
		 - Main content(right side part)
		-->
		<view style="position: absolute; left: 16%; top: 0; width: 81%; padding: 0 1.5%;">
			<u-row customStyle="margin-top: 10vh;">
				<u-col span="6">
					<view style="color: #6e6893; font-size: 40rpx; font-weight: bold;">VENUE MANAGEMENT</view>
				</u-col>
				
				<u-col span="6">
					<view style="text-align: right; color: #6e6893;">
						Total payments received:
						<text style="margin-left: 10rpx; color: #6d5bd0; font-size: 40rpx; font-weight: bold;">{{ formatPayment() }}£</text>
					</view>
				</u-col>
			</u-row>
			
			<view style="margin-top: 5rpx; border-bottom-style: solid; border-color: #c6c2de; border-width: 3rpx; width: 100%;"></view>
			
			<!-- Venues table -->
			<view style="border-radius: 30rpx; margin-top: 30rpx; box-shadow: 0 0 7px -3rpx #888888;;">
				<u-row customStyle="font-weight: bold; padding: 20rpx 0; color: #6e6893; background-color: #f4f2ff; border-radius: 30rpx 30rpx 0 0;">
					<u-col span="1">
						<view style="text-align: center;">ID</view>
					</u-col>
					<u-col span="2">
						<view style="text-align: center;">NAME</view>
					</u-col>
					<u-col span="1">
						<view style="text-align: center;">SPORT</view>
					</u-col>
					<u-col span="4">
						<view style="text-align: center;">LOCATION</view>
					</u-col>
					<u-col span="1">
						<view style="text-align: center;">CAPACITY</view>
					</u-col>
					<u-col span="2">
						<view style="text-align: center;">TIME SECTION</view>
					</u-col>
					<u-col span="1" customStyle="text-align: center;">
						<u-row justify="center">
							<u-icon name="more-dot-fill" color="#6e6893"></u-icon>
						</u-row>
					</u-col>
				</u-row>
				
				<block v-for="item, index in venues">
					<u-row customStyle="color: #606266; border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; padding: 30rpx 0;">
						<u-col span="1">
							<view style="text-align: center;">{{ item.id }}</view>
						</u-col>
						<u-col span="2">
							<view style="text-align: center;">{{ item.displayName }}</view>
						</u-col>
						<u-col span="1">
							<view style="text-align: center;">{{ sports[item.sport].name }}</view>
						</u-col>
						<u-col span="4">
							<view style="text-align: center;">{{ item.location }}</view>
						</u-col>
						<u-col span="1">
							<view style="text-align: center;">{{ item.capacity < 0 ? -item.capacity : item.capacity }}</view>
						</u-col>
						<u-col span="2">
							<view style="text-align: center;">
								{{ item.timeBlocks.length > 0 ? formatTime(item.timeBlocks[0].startTime) + " - " + formatTime(item.timeBlocks[item.timeBlocks.length-1].endTime) : "00:00 - 00:00" }}
							</view>
						</u-col>
						<u-col span="1" @click="showOptions(index)">
							<u-row justify="center">
								<u-icon name="more-dot-fill"></u-icon>
							</u-row>
						</u-col>
					</u-row>
				</block>
				<u-row v-if="venues.length==0"
					justify="center"
					customStyle="font-weight: bold; color: #909399; border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; padding: 30rpx 0;"
				>None available...</u-row>
				
				<!-- TODO: create a new venue -->
				<u-row
					@click="launchCreateVenue"
					justify="center"
					customStyle="border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; background-color: #f4f2ff; border-radius: 0 0 30rpx 30rpx; padding: 20rpx 0;">
					<u-icon name="plus" color="#6e6893"></u-icon>
					<view style="margin-left: 10rpx; color: #6e6893;">Create a new venue</view>
				</u-row>
			</view>
		</view>
		
		<u-popup :show="selectedVenue!=''" @close="selectedVenue=''" mode="center" round="30rpx">
			<view style="width: 600rpx;">
				<view style="padding: 30rpx; color: #6d5bd0; font-size: 36rpx; font-weight: bold;">{{ selectedVenue.displayName }} - options</view>
				<view
					@click="launchEdit"
					style="border-top-style: solid; border-width: 3rpx; border-color: #e2e2e2; text-align: center; padding: 25rpx 0; color: #6d6d6d;"
				>Edit properties</view>
				<view
					@click="showReservations"
					style="border-top-style: solid; border-width: 3rpx; border-color: #e2e2e2; text-align: center; padding: 25rpx 0; color: #6d6d6d;"
				>Reservations list</view>
				<view
					@click="showDelete"
					style="border-top-style: solid; border-width: 3rpx; border-color: #e2e2e2; text-align: center; padding: 25rpx 0; color: #f56c6c;"
				>Delete venue</view>
			</view>
		</u-popup>
		
		<u-popup :show="editingVenue!=''||showCreateVenue" mode="center" round="30rpx" :closeOnClickOverlay="false">
			<view style="padding: 40rpx; width: 40vw;">
				<view style="color: #6d5bd0; font-size: 36rpx; font-weight: bold;">{{ !showCreateVenue ? editingVenue.displayName + ' - edit' : "Create Venue" }}</view>
				<u--form>
					<view style="color: #6d6d6d; margin-top: 30rpx;">Name</view>
					<u-form-item>
						<u--input @change="checkForm" placeholder="Name" v-model="displayName"></u--input>
					</u-form-item>
					
					<view style="color: #6d6d6d; margin-top: 30rpx;">Location</view>
					<u-form-item>
						<u--input @change="checkForm" placeholder="Location" v-model="location"></u--input>
					</u-form-item>
					
					<view style="color: #6d6d6d; margin-top: 30rpx;">Sport</view>
					<u-button @click="showSportSelect=true" :text="sports[selectedSport].name" customStyle="margin-top: 20rpx;"></u-button>
					
					<view style="color: #6d6d6d; margin-top: 30rpx;">Capacity</view>
					<u-form-item>
						<u--input @blur="checkCapacity" placeholder="Capacity" v-model="capacity"></u--input>
					</u-form-item>
					
					<view style="color: #6d6d6d; margin-top: 30rpx;">Days Allow</view>
					<u-form-item>
						<u--input @blur="checkDaysAllow" placeholder="Days Allow" v-model="daysAllow"></u--input>
					</u-form-item>
					
					<view style="color: #6d6d6d; margin-top: 30rpx;">Time Blocks</view>
					<u-row customStyle="background-color: #f4f2ff; color: #6e6893; margin-top: 10rpx; padding: 10rpx 0;">
						<u-col span="6">
							<view style="text-align: center;">Time Section</view>
						</u-col>
						<u-col span="4">
							<view style="text-align: center;">Price</view>
						</u-col>
						<u-col span="2">
							<view style="text-align: center;">Operation</view>
						</u-col>
					</u-row>
					<u-row v-for="item, index in timeBlocks" :key="index"
						:customStyle="'padding: 10rpx 0; color: #606266; background-color:'+(index%2==0?'#fff;':'#f4f4f4;')"
					>
						<u-col span="6" @click="launchEditTimeBlock(index)">
							<view style="text-align: center;">{{ formatTime(item.startTime) + ' - ' + formatTime(item.endTime) }}</view>
						</u-col>
						<u-col span="4" @click="launchEditTimeBlock(index)">
							<view style="text-align: center;">{{ item.fee }}£</view>
						</u-col>
						<u-col span="2">
							<u-row justify="center">
								<u-icon color="#606266" @click="timeBlocks.splice(index, 1)" name="close"></u-icon>
							</u-row>
						</u-col>
					</u-row>
					<u-row v-if="timeBlocks.length==0" customStyle="padding: 10rpx 0; color: #888;" justify="center">
						No time block...
					</u-row>
					<u-row customStyle="background-color: #f4f2ff; padding: 10rpx 0;" @click="launchCreateTimeBlock" justify="center">
						<u-icon name="plus" color="#6e6893"></u-icon>
						<view style="margin-left: 10rpx; color: #6e6893;">Create a time block</view>
					</u-row>
					
					<u-row customStyle="margin-top: 30rpx;">
						<u-button @click="cancelClicked" text="Cancel" customStyle="margin-left: 60%; color: #909399; border-radius: 15rpx;"></u-button>
						<u-button @click="confirmVenue" :disabled="flag" text="Confirm" type="primary" customStyle="margin-left: 2.5%; border-radius: 15rpx;"></u-button>
					</u-row>
				</u--form>
			</view>
		</u-popup>
		
		<u-popup :show="showSportSelect" @close="showSportSelect=false" mode="center" round="30rpx" :overlay="false">
			<view style="width: 20vw; border-radius: 30rpx; border: 3rpx solid #f4f4f4; box-shadow: 0 0 15rpx #888;">
				<view style="padding: 30rpx; color: #6d5bd0; font-size: 36rpx; font-weight: bold;">Select Sport Type</view>
				<u-button v-for="item, index in sports" :key="index"
					:customStyle="'border-style: none; border-top-style: solid; border-radius: 0;'+(index==sports.length-1?'border-radius:0 0 30rpx 30rpx;':'')"
					:text="item.name"
					@click="selectSport(index)"
				></u-button>
			</view>
		</u-popup>
		
		<u-popup :show="editingTimeBlock!=''||showCreateTimeBlock" mode="center" round="30rpx" :overlay="false">
			<view style="padding: 40rpx; width: 30vw; box-shadow: 0 0 15rpx #888; border-radius: 30rpx;">
				<view style="color: #6d5bd0; font-size: 36rpx; font-weight: bold;">{{ showCreateTimeBlock?"Create Time Block":"Edit Time Block" }}</view>
				<u--form>
					<view style="color: #6d6d6d; margin-top: 30rpx;">Start Time</view>
					<u-form-item>
						<view style="font-size: 26rpx; color: #8e88b3; font-weight: bold; margin-right: 10rpx;">HOUR</view>
						<u--input @blur="checkTimeBlockForm" placeholder="Start Time(Hour)" v-model="startTimeHour"></u--input>
						
						<view style="font-size: 26rpx; color: #8e88b3; font-weight: bold; margin: 0 10rpx;">MIN</view>
						<u--input @blur="checkTimeBlockForm" placeholder="Start Time(Min)" v-model="startTimeMin"></u--input>
					</u-form-item>
					
					<view style="color: #6d6d6d; margin-top: 30rpx;">End Time</view>
					<u-form-item>
						<view style="font-size: 26rpx; color: #8e88b3; font-weight: bold; margin-right: 10rpx;">HOUR</view>
						<u--input @blur="checkTimeBlockForm" placeholder="End Time(Hour)" v-model="endTimeHour"></u--input>
						
						<view style="font-size: 26rpx; color: #8e88b3; font-weight: bold; margin: 0 10rpx;">MIN</view>
						<u--input @blur="checkTimeBlockForm" placeholder="End Time(Min)" v-model="endTimeMin"></u--input>
					</u-form-item>
					
					<view style="color: #6d6d6d; margin-top: 30rpx;">Price</view>
					<u-form-item>
						<u-input @blur="checkTimeBlockForm" placeholder="Price" v-model="price"></u-input>
						<text style="color: #8e88b3; font-size: 34rpx; font-weight: bold; margin-left: 10rpx;">£</text>
					</u-form-item>
					
					<u-row customStyle="margin-top: 30rpx;">
						<u-button @click="cancelTimeBlock" text="Cancel" customStyle="margin-left: 60%; color: #909399; border-radius: 15rpx;"></u-button>
						<u-button @click="confirmTimeBlock" :disabled="flag" text="Confirm" type="primary" customStyle="margin-left: 2.5%; border-radius: 15rpx;"></u-button>
					</u-row>
				</u--form>
			</view>
		</u-popup>
		
		<u-popup :show="reservations!==''" @close="reservations=''" mode="center" round="30rpx">
			<view style="width: 60vw; border-radius: 30rpx; border: 3rpx solid #f4f4f4;">
				<view style="padding: 30rpx; color: #6d5bd0; font-size: 36rpx; font-weight: bold;">Reservation List</view>
				
				<u-row customStyle="background-color: #f4f2ff; color: #6e6893; margin-top: 10rpx; padding: 20rpx 0;">
					<u-col span="1">
						<view style="text-align: center;">Id</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">Username</view>
					</u-col>
					<u-col span="4">
						<view style="text-align: center;">Time Section</view>
					</u-col>
					<u-col span="1">
						<view style="text-align: center;">Price</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">Status</view>
					</u-col>
				</u-row>
				<u-row v-for="item, index in reservations" :key="index"
					customStyle="padding: 20rpx 0; color: #606266; border-color: #d9d5ec; border-top-style: solid; border-width: 3rpx;"
				>
					<u-col span="1">
						<view style="text-align: center;">{{ item.id }}</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">{{ item.username }}</view>
					</u-col>
					<u-col span="4">
						<view style="text-align: center;">{{ formatDateTime(item.startTime) + ' - ' + formatTime(item.endTime) }}</view>
					</u-col>
					<u-col span="1">
						<view style="text-align: center;">{{ item.cost }}£</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">
							<block v-if="item.endTime > new Date().getTime()">
								<view class="reservation-status" v-if="item.status==0" style="color: #ff9900;">Waiting</view>
								<view class="reservation-status" v-else-if="item.status==1" style="color: #19be6b;">Ontime</view>
								<view class="reservation-status" v-else style="color: #f56cfc;">Canceled</view>
							</block>
							<block v-else>
								<view class="reservation-status" v-if="item.status==0" style="color: #f56c6c;">Overdue</view>
								<view class="reservation-status" v-else-if="item.status==1" style="color: #2979ff;">Finish</view>
								<view class="reservation-status" v-else style="color: #f56cfc;">Canceled</view>
							</block>
						</view>
					</u-col>
				</u-row>
				<u-row v-if="reservations.length==0" customStyle="padding: 10rpx 0;  border-color: #d9d5ec; border-top-style: solid; border-width: 3rpx;" justify="center">
					No Reservation...
				</u-row>
				<u-row customStyle="background-color: #f4f2ff; padding: 10rpx 0; height: 40rpx; border-color: #d9d5ec; border-top-style: solid; border-width: 3rpx;" justify="center">
				</u-row>
				
				<u-row justify="end">
					<u-button @click="reservations=''" text="Back" type="primary" customStyle="margin: 30rpx; width: 300rpx; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<u-popup :show="deleting!=''" @close="deleting=''" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #f56c6c; font-size: 40rpx; font-weight: bold;">WARNING</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">
					This operation can not be undo! Are your sure you want to delete this venue?
				</view>
				
				<u-row>
					<u-button @click="deleting=''" text="Cancel" customStyle="margin-left: 50%; color: #909399; border-radius: 15rpx;"></u-button>
					<u-button @click="deleteVenue" text="Delete" color="#f56c6c" customStyle="margin-left: 5%; border-radius: 15rpx;"></u-button>
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
			
			<u-row customStyle="margin-top: 25%;">
				<image
					src="../../static/icon/venues-active.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="font-weight: bold; margin-left: 30rpx;">Sports Grounds</view>
			</u-row>
			
			<u-row @click="gotoUserRequest" customStyle="margin-top: 10%;">
				<image
					src="../../static/icon/user-request.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="font-weight: bold; margin-left: 30rpx; color: #999;">User Requests</view>
			</u-row>
			
			<block v-if="auth=='manager'">
				<u-row @click="gotoEmployee" customStyle="margin-top: 10%;">
					<image
						src="../../static/icon/employee.png"
						mode="aspectFit"
						style="height: 40rpx; width: 40rpx; margin-left: 15%;"
					></image>
					<view style="font-weight: bold; margin-left: 30rpx; color: #999;">Employees</view>
				</u-row>
				
				<view style="margin: 40% 0 0 15%; border-width: 3rpx; border-color: #e2e2e2; width: 70%; border-bottom-style: solid;"></view>
			</block>
			<!-- TODO: place this in the right place -->
			<view v-else style="margin: 40% 0 0 15%; border-width: 3rpx; border-color: #e2e2e2; width: 70%; border-bottom-style: solid;"></view>
			
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
				auth: 'employee',
				
				venues: [ ],
				
				selectedVenue: '',
				editingVenue: '',
				showSportSelect: false,
				showCreateVenue: false,
				editingTimeBlock: '',
				showCreateTimeBlock: false,
				deleting: '',
				reservations: '',
				
				// Data for update information of a venue
				displayName: '',
				location: '',
				capacity: '',
				oriCapacity: '',
				daysAllow: '',
				oriDaysAllow: '',
				selectedSport: 0,
				timeBlocks: [ ],
				
				startTimeHour: '',
				oriStartTimeHour: '',
				startTimeMin: '',
				oriStartTimeMin: '',
				endTimeHour: '',
				oriEndTimeHour: '',
				endTimeMin: '',
				oriEndTimeMin: '',
				price: '',
				oriPrice: '',
				
				totalPayment: 0,
				
				sports: [ { "name": "???" } ],
				
				flag: false,
				
				showLogout: false,
			}
		},
		
		async onLoad() {
			let res = await uni.$u.http.post("/check-authority");
			this.auth = res.authority;
			
			let venues = await uni.$u.http.post("/manage-venue");
			this.sports = await uni.$u.http.post("/sports");
			
			for(let i in venues)
			{
				for(let j in this.sports)
					if(this.sports[j].id == venues[i].activityId)
						venues[i].sport = j;
				
				// Pre-process time blocks
				let blocks = await uni.$u.http.post("/time-blocks", { }, { params: { venueId: venues[i].id } });
				blocks.sort((a, b) => a.startTime - b.startTime);
				venues[i].timeBlocks = blocks;
			}
			this.venues = venues;
			
			res = await uni.$u.http.post("/total-payments");
			this.totalPayment = res.val;
		},
		
		methods: {
			async showReservations() {
				let res = await uni.$u.http.post(
					"/venue-reservations",
					{ },
					{
						params: {
							venueId: this.selectedVenue.id
						}
					},
				);
				
				for(let i in res)
				{
					let user = await uni.$u.http.post("/of-user", { }, { params: { userId: res[i].userId } });
					res[i].username = user.displayName;
				}
				
				this.reservations = res;
				this.selectedVenue = '';
			},
			
			async deleteVenue() {
				let res = await uni.$u.http.post(
					"/venue-del",
					{ },
					{
						params: {
							venueId: this.deleting.id
						}
					},
				);
								if(res.status != 200)
					uni.showToast({
						title: res.message,
						icon:"error",
						duration:3000
					});
				else uni.$u.route({
					url: "pages/index/middle",
					type: "redirect",
					params: {
						url: "pages/sub/manage"
					}
				});
			},
			
			showDelete() {
				this.deleting = this.selectedVenue;
				this.selectedVenue = '';
			},
			
			cancelTimeBlock() {
				this.editingTimeBlock = '';
				this.showCreateTimeBlock = false;
			},
			
			launchEditTimeBlock(index) {
				let block = this.editingTimeBlock = this.timeBlocks[index];
				let start = new Date(block.startTime + 16 * 60 * 60 * 1000);
				let end = new Date(block.endTime + 16 * 60 * 60 * 1000);
				
				this.startTimeHour
					= this.oriStartTimeHour
					= start.getHours() + '';
				this.startTimeMin
					= this.oriStartTimeMin
					= start.getMinutes() + '';
				this.endTimeHour
					= this.oriEndTimeHour
					= end.getHours() + '';
				this.endTimeMin
					= this.oriEndTimeMin
					= end.getMinutes() + '';
				this.price
					= this.oriPrice
					= block.fee + '';
			},
			
			confirmTimeBlock() {
				const MIN = 60 * 1000;
				const HOUR = 60 * MIN;
				if(this.showCreateTimeBlock)
				{
					this.timeBlocks.push({
						startTime: this.startTimeHour * HOUR + this.startTimeMin * MIN,
						endTime: this.endTimeHour * HOUR + this.endTimeMin * MIN,
						fee: this.price * 1
					});
					
					this.timeBlocks.sort((a, b) => a.startTime - b.startTime);
				}
				else
				{
					this.editingTimeBlock.startTime = this.startTimeHour * HOUR + this.startTimeMin * MIN;
					this.editingTimeBlock.endTime = this.endTimeHour * HOUR + this.endTimeMin * MIN;
					this.editingTimeBlock.fee = this.price * 1;
				}
				
				this.editingTimeBlock = '';
				this.showCreateTimeBlock = false;
			},
			
			launchCreateTimeBlock() {
				this.startTimeHour
					= this.oriStartTimeHour
					= '0';
				this.startTimeMin
					= this.oriStartTimeMin
					= '0';
				this.endTimeHour
					= this.oriEndTimeHour
					= '0';
				this.endTimeMin
					= this.oriEndTimeMin
					= '0';
				this.price
					= this.oriPrice
					= '3';
				
				this.showCreateTimeBlock = true;
			},
			
			checkTimeBlockForm() {
				let time = [ this.startTimeHour, this.startTimeMin, this.endTimeHour, this.endTimeMin ];
				let oriTime = [ this.oriStartTimeHour, this.oriStartTimeMin, this.oriEndTimeHour, this.oriEndTimeMin ];
				for(let i in time)
				{
					let reg = time[i].match(/[0-9]+/);
					if(reg && reg[0] == time[i])
						oriTime[i] = time[i];
					else time[i] = oriTime[i];
				}
				this.startTimeHour = time[0];
				this.startTimeMin = time[1];
				this.endTimeHour = time[2];
				this.endTimeMin = time[3];
				this.oriStartTimeHour = oriTime[0];
				this.oriStartTimeMin = oriTime[1];
				this.oriEndTimeHour = oriTime[2];
				this.oriEndTimeMin = oriTime[3];
				
				let reg = this.price.match(/[0-9]+(\.[0-9]+)?/);
				if(reg && reg[0] == this.price)
					this.oriPrice = this.price;
				else this.price = this.oriPrice;
			},
			
			cancelClicked() {
				this.editingVenue = '';
				this.showCreateVenue = false;
			},
			
			formatTime(time) {
				let date = new Date(time + 16 * 60 * 60 * 1000);
				let hour = date.getHours();
				let min = date.getMinutes();
				return (hour < 10 ? '0' + hour : hour) + ":" + (min < 10 ? '0' + min : min);
			},
			
			async withTimeBlocks(venueId, blocks) {
				for(let i in blocks)
				{
					let res = await uni.$u.http.post(
						"/add-time-block",
						{ },
						{
							params: {
								venueId: venueId,
								startTime: blocks[i].startTime,
								endTime: blocks[i].endTime,
								price: blocks[i].fee
							}
						}
					);
					
					if(res.status != 200)
						return res.message;
				}
				
				return '';
			},
			
			async confirmVenue() {
				let errMsg = '';
				
				// Create a new venue
				if(this.showCreateVenue)
				{
					let res = await uni.$u.http.post(
						"/venue-create",
						{ },
						{
							params: {
								name: this.displayName,
								location: this.location,
								sportId: this.sports[this.selectedSport].id,
								capacity: this.capacity,
								daysAllow: this.daysAllow
							}
						},
					);
					
					errMsg = res.status == 200 ? await this.withTimeBlocks(res.id, this.timeBlocks) : res.message;
				}
				
				// Editing venue
				else switch(1)
				{
				default:
					let res = await uni.$u.http.post(
						"/venue-edit",
						{ },
						{
							params: {
								venueId: this.editingVenue.id,
								name: this.displayName,
								location: this.location,
								sportId: this.sports[this.selectedSport].id,
								capacity: this.capacity,
								daysAllow: this.daysAllow
							}
						},
					);
					
					if(res.status != 200)
					{
						errMsg = res.message;
						break;
					}
					
					res = await uni.$u.http.post(
						"/delete-time-blocks",
						{ },
						{
							params: {
								venueId: this.editingVenue.id
							}
						},
					);
					
					errMsg = res.status == 200 ? await this.withTimeBlocks(this.editingVenue.id, this.timeBlocks) : res.message;
				}
				
				if(errMsg != '')
					uni.showToast({
						title: errMsg,
						duration: 2000,
						icon: "error"
					});
				// Re-enter this page to refresh the data
				else uni.$u.route({
					url: "pages/index/middle",
					type: "redirect",
					params: {
						url: "pages/sub/manage"
					}
				});
			},
			
			showOptions(index) {
				console.log("show operations :" + index);
				this.selectedVenue = this.venues[index];
			},
			
			launchCreateVenue() {
				this.showCreateVenue = true;
				
				this.displayName = '';
				this.location = '';
				this.selectedSport = 0;
				this.capacity
					= this.oriCapacity
					= '10';
				this.daysAllow
					= this.oriDaysAllow
					= '3';
				this.timeBlocks = [ ];
				
				this.flag = true;
			},
			
			launchEdit() {
				this.editingVenue = this.selectedVenue;
				this.selectedVenue = '';
				
				this.displayName = this.editingVenue.displayName;
				this.location = this.editingVenue.location;
				this.selectedSport = this.editingVenue.sport;
				this.capacity
					= this.oriCapacity
					= this.editingVenue.capacity + '';
				this.daysAllow
					= this.oriDaysAllow
					= this.editingVenue.daysAllow + '';
				
				this.timeBlocks = [ ];
				for(let i in this.editingVenue.timeBlocks)
					this.timeBlocks[i] = { ...this.editingVenue.timeBlocks[i] };
				
				this.flag = false;
			},
			
			checkForm() {
				this.flag = this.displayName == '' || this.location == '';
			},
			
			selectSport(index) {
				this.selectedSport = index;
				this.showSportSelect = false;
			},
			
			checkCapacity() {
				let reg = this.capacity.match(/[0-9]+/);
				if(reg && reg[0] == this.capacity)
					this.oriCapacity = this.capacity;
				else this.capacity = this.oriCapacity;
			},
			
			checkDaysAllow() {
				let reg = this.daysAllow.match(/[0-9]+/);
				if(reg && reg[0] == this.daysAllow)
					this.oriDaysAllow = this.daysAllow;
				else this.daysAllow = this.oriDaysAllow;
			},
			
			logout() {
				uni.removeStorageSync("token");
				uni.$u.route({
					url: "pages/auth/login",
					type: "reLaunch"
				})
			},
			
			formatPayment() {
				let reg = (this.totalPayment + '').match(/[0-9]+\.[0-9]+/);
				return !reg ? this.totalPayment + '.0' : this.totalPayment;
			},
			
			gotoEmployee() {
				uni.$u.route({
					url: "pages/sub/employee",
					type: "reLaunch"
				});
			},
			
			gotoUserRequest() {
				uni.$u.route({
					url: "pages/sub/user-request",
					type: "reLaunch"
				});
			},
			
			formatDateTime(time) {
				let date = new Date(time);
				let hour = date.getHours();
				let min = date.getMinutes();
				return(
					(date.getMonth() + 1)
					+ "/" + date.getDay()
					+ "/" + date.getFullYear()
					+ " " + (hour < 10 ? '0' + hour : hour)
					+ ":" + (min < 10 ? '0' + min : min)
				);
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
