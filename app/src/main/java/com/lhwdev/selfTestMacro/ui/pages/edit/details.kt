package com.lhwdev.selfTestMacro.ui.pages.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lhwdev.selfTestMacro.R
import com.lhwdev.selfTestMacro.database.*
import com.lhwdev.selfTestMacro.navigation.LocalNavigator
import com.lhwdev.selfTestMacro.navigation.Navigator
import com.lhwdev.selfTestMacro.ui.LocalPreference
import com.lhwdev.selfTestMacro.ui.scheduleInfo
import com.vanpra.composematerialdialogs.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


internal suspend fun showDetailsFor(
	navigator: Navigator,
	group: DbTestGroup,
	scope: CoroutineScope
): Unit = navigator.showDialogUnit { removeRoute ->
	val target = group.target
	val pref = LocalPreference.current
	
	val groupName = with(pref.db) { target.name }
	
	Scaffold(
		topBar = {
			Column {
				TopAppBar(
					navigationIcon = {
						IconButton(onClick = removeRoute) {
							Icon(
								painterResource(R.drawable.ic_clear_24),
								contentDescription = "닫기"
							)
						}
					},
					title = {
						if(target is DbTestTarget.Group) {
							Text("$groupName (${group.target.allUserIds.size}명)")
						} else {
							Text(groupName)
						}
					},
					backgroundColor = Color.Transparent,
					elevation = 0.dp
				)
				Divider()
			}
		}
	) {
		EditUserDetail(removeRoute = removeRoute, target = target, group = group)
	}
}

@Composable
private fun EditUserDetail(
	removeRoute: () -> Unit,
	target: DbTestTarget,
	group: DbTestGroup
) {
	val navigator = LocalNavigator
	val pref = LocalPreference.current
	val scope = rememberCoroutineScope()
	val groupName = with(pref.db) { target.name }
	
	fun Modifier.clickAction(block: suspend () -> Unit): Modifier = clickable {
		removeRoute()
		scope.launch { block() }
	}
	
	when(target) {
		is DbTestTarget.Single -> Column {
			ListItem(
				icon = {
					Icon(
						painterResource(R.drawable.ic_account_circle_24),
						contentDescription = null
					)
				},
				text = { Text(with(pref.db) { target.user.institute.name }) },
				secondaryText = { Text("자가진단 예약: ${group.scheduleInfo()}") }
			)
			
			ListItem(modifier = Modifier.clickAction {
				val doDelete = navigator.promptYesNoDialog(
					title = { Text("${with(pref.db) { target.name }}을(를) 삭제할까요?") }
				)
				
				if(doDelete == true) pref.db.removeTestGroup(group)
			}) { Text("사용자 삭제") }
			
			val list = pref.db.testGroups.groups.filter {
				it.target is DbTestTarget.Group
			}
			
			if(list.isNotEmpty()) ListItem(modifier = Modifier.clickAction {
				val moveTarget = navigator.showDialog<DbTestGroup> { removeRoute ->
					Title { Text("이동할 그룹 선택") }
					ListContent {
						Column {
							for(item in list) {
								val itemTarget = item.target as DbTestTarget.Group
								ListItem(
									text = { Text(itemTarget.name) },
									secondaryText = { Text(with(pref.db) { itemTarget.allUsers.joinToString { it.name } }) },
									modifier = Modifier.clickable {
										removeRoute(item)
									}
								)
							}
						}
					}
					Buttons {
						NegativeButton { Text("취소") }
					}
				}
				
				if(moveTarget != null) pref.db.moveToTestGroup(
					target = listOf(group),
					toGroup = moveTarget
				)
			}) { Text("그룹 이동") }
		}
		is DbTestTarget.Group -> Column {
			// group members
			ListItem(
				icon = {
					Icon(
						painterResource(R.drawable.ic_group_24),
						contentDescription = null
					)
				},
				secondaryText = { Text("자가진단 예약: ${group.scheduleInfo()}") }
			) {
				val text = with(pref.db) { target.allUsers }.joinToString { it.name }
				Text(text)
			}
			
			// rename
			ListItem(modifier = Modifier.clickAction {
				navigator.showDialogAsync { SetupGroup(previousGroup = group) }
			}) {
				Text("그룹 수정")
			}
			
			
			// disband group
			ListItem(modifier = Modifier.clickAction {
				var inheritSchedule by mutableStateOf(false)
				
				val doDisband = navigator.promptYesNoDialog(
					title = { Text("${groupName}을(를) 해제할까요?") },
					content = {
						ListItem(
							icon = {
								Checkbox(
									checked = inheritSchedule,
									onCheckedChange = null
								)
							},
							text = { Text("그룹의 자가진단 예약 상태 유지") },
							modifier = Modifier.clickable {
								inheritSchedule = !inheritSchedule
							}
						)
					}
				)
				
				if(doDisband == true) {
					pref.db.disbandGroup(group, inheritSchedule = inheritSchedule)
				}
			}) {
				Text("그룹 해제")
			}
			
			// remove group and group members entirely
			ListItem(modifier = Modifier.clickAction {
				val doDelete = navigator.promptYesNoDialog(
					title = { Text("${groupName}을(를) 삭제할까요?") }
				)
				
				if(doDelete == true) pref.db.removeTestGroup(group)
			}) { Text("그룹과 그룹원 삭제") }
		}
		
	}
}