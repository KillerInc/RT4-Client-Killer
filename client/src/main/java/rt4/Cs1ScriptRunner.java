package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;
import plugin.PluginRepository;

import java.nio.charset.StandardCharsets;

public class Cs1ScriptRunner {
	@OriginalMember(owner = "client!bm", name = "p", descriptor = "Lclient!na;")
	public static final JagString CS1_PLACEHOLDER_1 = JagString.parse("(U1");
	@OriginalMember(owner = "client!wh", name = "u", descriptor = "Lclient!na;")
	public static final JagString CS1_PLACEHOLDER_2 = JagString.parse("(U2");
	@OriginalMember(owner = "client!mj", name = "g", descriptor = "Lclient!na;")
	public static final JagString CS1_PLACEHOLDER_3 = JagString.parse("(U3");
	@OriginalMember(owner = "client!hh", name = "a", descriptor = "Lclient!na;")
	public static final JagString CS1_PLACEHOLDER_4 = JagString.parse("(U4");
	@OriginalMember(owner = "client!tb", name = "P", descriptor = "Lclient!na;")
	public static final JagString CS1_PLACEHOLDER_5 = JagString.parse("(U5");
	@OriginalMember(owner = "client!jh", name = "g", descriptor = "Lclient!na;")
	public static final JagString CS1_PLACEHOLDER_DNS = JagString.parse("(Udns");
	@OriginalMember(owner = "client!lh", name = "z", descriptor = "Lclient!na;")
	public static final JagString CACHE_STAT_SEPARATOR = JagString.parse("(U (X");
	@OriginalMember(owner = "client!bd", name = "b", descriptor = "Lclient!na;")
	public static final JagString CACHE_STAT_SUFFIX = JagString.parse("(U(Y");
	@OriginalMember(owner = "client!oj", name = "p", descriptor = "I")
	public static final int SCROLLBAR_TRACK_COLOR = 0x23201b;
	@OriginalMember(owner = "client!ec", name = "l", descriptor = "I")
	public static final int SCROLLBAR_THUMB_COLOR = 0x4d4233;
	@OriginalMember(owner = "client!rl", name = "Z", descriptor = "I")
	public static final int SCROLLBAR_THUMB_HIGHLIGHT_COLOR = 0x766654;
	@OriginalMember(owner = "client!bj", name = "V", descriptor = "I")
	public static final int SCROLLBAR_THUMB_SHADOW_COLOR = 0x332d25;
	@OriginalMember(owner = "client!pg", name = "V", descriptor = "I")
	public static final int TOOLTIP_DISPLAY_DELAY = 50;
	@OriginalMember(owner = "client!th", name = "m", descriptor = "[Lclient!be;")
	public static Component[] deferredDragComponents;
	@OriginalMember(owner = "client!k", name = "j", descriptor = "I")
	public static int deferredDragRenderY;
	@OriginalMember(owner = "client!gf", name = "K", descriptor = "I")
	public static int deferredDragRenderX;
	@OriginalMember(owner = "client!ac", name = "p", descriptor = "Lclient!be;")
	public static Component dragParentComponent = null;
	@OriginalMember(owner = "client!ib", name = "e", descriptor = "Lclient!be;")
	public static Component draggedComponent = null;
	@OriginalMember(owner = "client!km", name = "pc", descriptor = "Z")
	public static boolean isDragging = false;
	@OriginalMember(owner = "client!gg", name = "db", descriptor = "I")
	public static int dragBoundsMinX = -1;
	@OriginalMember(owner = "client!nb", name = "d", descriptor = "I")
	public static int dragStartMouseY = 0;
	@OriginalMember(owner = "client!kd", name = "Bb", descriptor = "I")
	public static int tooltipRenderX = -1;
	@OriginalMember(owner = "client!d", name = "R", descriptor = "Lclient!be;")
	public static Component pleaseWaitComponent = null;
	@OriginalMember(owner = "client!hi", name = "a", descriptor = "I")
	public static int gameSceneTooltipX = -1;
	@OriginalMember(owner = "client!ld", name = "c", descriptor = "I")
	public static int tooltipRenderY = -1;
	@OriginalMember(owner = "client!lf", name = "k", descriptor = "I")
	public static int cachedActiveSpriteId = -1;
	@OriginalMember(owner = "client!mh", name = "X", descriptor = "I")
	public static int cachedDefaultSpriteId = -1;
	@OriginalMember(owner = "client!ig", name = "b", descriptor = "I")
	public static int worldMapViewportX;
	@OriginalMember(owner = "client!ig", name = "f", descriptor = "I")
	public static int worldMapViewportY;
	@OriginalMember(owner = "client!hc", name = "P", descriptor = "I")
	public static int mapHighlightPulseCounter;
	@OriginalMember(owner = "client!u", name = "a", descriptor = "I")
	public static int dragStartMouseX = 0;
	@OriginalMember(owner = "client!re", name = "y", descriptor = "I")
	public static int dragElapsedTicks;
	@OriginalMember(owner = "client!em", name = "z", descriptor = "Z")
	public static boolean isMenuOpen = false;

	@OriginalMember(owner = "client!we", name = "a", descriptor = "(BILclient!be;)I")
	public static int run(@OriginalArg(1) int scriptIndex, @OriginalArg(2) Component component) {
		if (component.cs1Scripts == null || scriptIndex >= component.cs1Scripts.length) {
			return -2;
		}
		try {
			@Pc(33) int[] script = component.cs1Scripts[scriptIndex];
			@Pc(35) byte accumulatorMode = 0;
			@Pc(37) int accumulator = 0;
			@Pc(39) int pc = 0;
			while (true) {
				@Pc(41) int value = 0;
				@Pc(46) int opcode = script[pc++];
				@Pc(48) byte nextAccumulatorMode = 0;
				if (opcode == 0) {
					return accumulator;
				}
				if (opcode == 15) {
					nextAccumulatorMode = 1;
				}
				if (opcode == 16) {
					nextAccumulatorMode = 2;
				}
				if (opcode == 1) {
					value = PlayerSkillXpTable.boostedLevels[script[pc++]];
				}
				if (opcode == 17) {
					nextAccumulatorMode = 3;
				}
				if (opcode == 2) {
					value = PlayerSkillXpTable.baseLevels[script[pc++]];
				}
				if (opcode == 3) {
					value = PlayerSkillXpTable.experience[script[pc++]];
				}
				@Pc(124) int pc2;
				@Pc(135) Component otherComponent;
				@Pc(140) int pc3;
				@Pc(152) int i;
				if (opcode == 4) {
					pc2 = script[pc++] << 16;
					@Pc(131) int componentId = pc2 + script[pc++];
					otherComponent = InterfaceList.getComponent(componentId);
					pc3 = script[pc++];
					if (pc3 != -1 && (!ObjTypeList.get(pc3).members || LoginManager.mapMembers)) {
						for (i = 0; i < otherComponent.objTypes.length; i++) {
							if (pc3 + 1 == otherComponent.objTypes[i]) {
								value += otherComponent.objCounts[i];
							}
						}
					}
				}
				if (opcode == 5) {
					value = VarpDomain.activeVarps[script[pc++]];
				}
				if (opcode == 6) {
					value = PlayerSkillXpTable.xpLevelLookup[PlayerSkillXpTable.baseLevels[script[pc++]] - 1];
				}
				if (opcode == 7) {
					value = VarpDomain.activeVarps[script[pc++]] * 100 / 46875;
				}
				if (opcode == 8) {
					value = PlayerList.self.combatLevel;
				}
				if (opcode == 9) {
					for (pc2 = 0; pc2 < 25; pc2++) {
						if (PlayerSkillXpTable.ENABLED_SKILLS[pc2]) {
							value += PlayerSkillXpTable.baseLevels[pc2];
						}
					}
				}
				if (opcode == 10) {
					pc2 = script[pc++] << 16;
					pc2 += script[pc++];
					otherComponent = InterfaceList.getComponent(pc2);
					pc3 = script[pc++];
					if (pc3 != -1 && (!ObjTypeList.get(pc3).members || LoginManager.mapMembers)) {
						for (i = 0; i < otherComponent.objTypes.length; i++) {
							if (otherComponent.objTypes[i] == pc3 + 1) {
								value = 999999999;
								break;
							}
						}
					}
				}
				if (opcode == 11) {
					value = Player.runEnergy;
				}
				if (opcode == 12) {
					value = Player.weight;
				}
				if (opcode == 13) {
					pc2 = VarpDomain.activeVarps[script[pc++]];
					@Pc(353) int bitIndex = script[pc++];
					value = (0x1 << bitIndex & pc2) == 0 ? 0 : 1;
				}
				if (opcode == 14) {
					pc2 = script[pc++];
					value = VarpDomain.getVarbit(pc2);
				}
				if (opcode == 18) {
					value = (PlayerList.self.xFine >> 7) + Camera.originX;
				}
				if (opcode == 19) {
					value = (PlayerList.self.yFine >> 7) + Camera.originY;
				}
				if (opcode == 20) {
					value = script[pc++];
				}
				if (nextAccumulatorMode == 0) {
					if (accumulatorMode == 0) {
						accumulator += value;
					}
					if (accumulatorMode == 1) {
						accumulator -= value;
					}
					if (accumulatorMode == 2 && value != 0) {
						accumulator /= value;
					}
					if (accumulatorMode == 3) {
						accumulator *= value;
					}
					accumulatorMode = 0;
				} else {
					accumulatorMode = nextAccumulatorMode;
				}
			}
		} catch (@Pc(464) Exception ignored) {
			return -1;
		}
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(Lclient!be;Lclient!na;I)Lclient!na;")
	public static JagString interpolate(@OriginalArg(0) Component component, @OriginalArg(1) JagString text) {
		if (text.indexOf(JagString.PERCENT_SIGN) == -1) {
			return text;
		}

		while (true) {
			@Pc(14) int index = text.indexOf(CS1_PLACEHOLDER_1);
			if (index == -1) {
				while (true) {
					index = text.indexOf(CS1_PLACEHOLDER_2);
					if (index == -1) {
						while (true) {
							index = text.indexOf(CS1_PLACEHOLDER_3);
							if (index == -1) {
								while (true) {
									index = text.indexOf(CS1_PLACEHOLDER_4);
									if (index == -1) {
										while (true) {
											index = text.indexOf(CS1_PLACEHOLDER_5);
											if (index == -1) {
												while (true) {
													index = text.indexOf(CS1_PLACEHOLDER_DNS);
													if (index == -1) {
														return text;
													}
													@Pc(246) JagString dnsString = JagString.EMPTY;
													if (Player.lastLogAddress != null) {
														dnsString = JagString.formatIp(Player.lastLogAddress.intArg2);
														if (Player.lastLogAddress.result != null) {
															@Pc(265) byte[] dnsBytes = ((String) Player.lastLogAddress.result).getBytes(StandardCharsets.ISO_8859_1);
															dnsString = JagString.decodeString(dnsBytes, dnsBytes.length, 0);
														}
													}
													text = JagString.concatenate(new JagString[]{text.substring(index, 0), dnsString, text.substring(index + 4)});
												}
											}
											text = JagString.concatenate(new JagString[]{text.substring(index, 0), StringUtils.toString(run(4, component)), text.substring(index + 2)});
										}
									}
									text = JagString.concatenate(new JagString[]{text.substring(index, 0), StringUtils.toString(run(3, component)), text.substring(index + 2)});
								}
							}
							text = JagString.concatenate(new JagString[]{text.substring(index, 0), StringUtils.toString(run(2, component)), text.substring(index + 2)});
						}
					}
					text = JagString.concatenate(new JagString[]{text.substring(index, 0), StringUtils.toString(run(1, component)), text.substring(index + 2)});
				}
			}
			text = JagString.concatenate(new JagString[]{text.substring(index, 0), StringUtils.toString(run(0, component)), text.substring(index + 2)});
		}
	}

	@OriginalMember(owner = "client!md", name = "a", descriptor = "(Lclient!be;I)Z")
	public static boolean isTrue(@OriginalArg(0) Component component) {
		if (component.cs1ComparisonOpcodes == null) {
			return false;
		}
		for (@Pc(14) int i = 0; i < component.cs1ComparisonOpcodes.length; i++) {
			@Pc(34) int value = run(i, component);
			@Pc(39) int operand = component.cs1ComparisonOperands[i];
			if (component.cs1ComparisonOpcodes[i] == 2) {
				if (operand <= value) {
					return false;
				}
			} else if (component.cs1ComparisonOpcodes[i] == 3) {
				if (value <= operand) {
					return false;
				}
			} else if (component.cs1ComparisonOpcodes[i] == 4) {
				if (value == operand) {
					return false;
				}
			} else if (operand != value) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(III[Lclient!be;IIIIBI)V")
	public static void renderComponent(@OriginalArg(0) int clipLeft, @OriginalArg(1) int parentY, @OriginalArg(2) int parentX, @OriginalArg(3) Component[] components, @OriginalArg(4) int clipRight, @OriginalArg(5) int layer, @OriginalArg(6) int clipTop, @OriginalArg(7) int clipBottom, @OriginalArg(9) int parentRectangle) {
		if (GlRenderer.enabled) {
			GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
		} else {
			SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
			Rasteriser.prepare();
		}

		for (@Pc(18) int i = 0; i < components.length; i++) {
			@Pc(30) Component component = components[i];
			if (component != null && (component.overlayer == layer || layer == 0xabcdabcd && component == draggedComponent)) {
				@Pc(57) int rectangle;
				if (parentRectangle == -1) {
					InterfaceList.rectangleX[InterfaceList.rectangles] = parentX + component.x;
					InterfaceList.rectangleY[InterfaceList.rectangles] = component.y + parentY;
					InterfaceList.rectangleWidth[InterfaceList.rectangles] = component.width;
					InterfaceList.rectangleHeight[InterfaceList.rectangles] = component.height;
					rectangle = InterfaceList.rectangles++;
				} else {
					rectangle = parentRectangle;
				}
				component.rectangleLoop = client.loop;
				component.rectangle = rectangle;
				if (!component.if3 || !InterfaceList.isHidden(component)) {
					if (component.clientCode > 0) {
						applyClientCode(component);
					}
					@Pc(114) int componentY = parentY + component.y;
					@Pc(117) int alpha = component.alpha;
					@Pc(123) int componentX = component.x + parentX;
					if (Cheat.qaOpTest && (InterfaceList.getServerActiveProperties(component).events != 0 || component.type == 0) && alpha > 127) {
						alpha = 127;
					}
					@Pc(166) int drawLeft;
					@Pc(164) int drawTop;
					if (component == draggedComponent) {
						if (layer != 0xabcdabcd && !component.dragRenderBehavior) {
							deferredDragRenderX = parentX;
							deferredDragRenderY = parentY;
							deferredDragComponents = components;
							continue;
						}
						if (isDragging && InterfaceList.dragParentFound) {
							drawTop = Mouse.lastMouseY;
							drawLeft = Mouse.lastMouseX;
							drawTop -= dragStartMouseY;
							if (drawTop < InterfaceList.dragParentY) {
								drawTop = InterfaceList.dragParentY;
							}
							if (drawTop + component.height > dragParentComponent.height + InterfaceList.dragParentY) {
								drawTop = dragParentComponent.height + InterfaceList.dragParentY - component.height;
							}
							componentY = drawTop;
							drawLeft -= dragStartMouseX;
							if (dragBoundsMinX > drawLeft) {
								drawLeft = dragBoundsMinX;
							}
							if (dragParentComponent.width + dragBoundsMinX < component.width + drawLeft) {
								drawLeft = dragParentComponent.width + dragBoundsMinX - component.width;
							}
							componentX = drawLeft;
						}
						if (!component.dragRenderBehavior) {
							alpha = 128;
						}
					}
					@Pc(302) int drawRight;
					@Pc(291) int drawBottom;
					@Pc(270) int temp1;
					@Pc(276) int temp2;
					if (component.type == 2) {
						drawBottom = clipBottom;
						drawRight = clipRight;
						drawTop = clipTop;
						drawLeft = clipLeft;
					} else {
						drawTop = componentY > clipTop ? componentY : clipTop;
						drawLeft = clipLeft < componentX ? componentX : clipLeft;
						temp1 = component.width + componentX;
						temp2 = componentY + component.height;
						if (component.type == 9) {
							temp2++;
							temp1++;
						}
						drawBottom = clipBottom <= temp2 ? clipBottom : temp2;
						drawRight = temp1 >= clipRight ? clipRight : temp1;
					}
					if (!component.if3 || drawRight > drawLeft && drawTop < drawBottom) {
						@Pc(468) int temp3;
						@Pc(503) int memory;
						@Pc(514) int color;
						@Pc(518) int cardMemory;
						@Pc(556) int temp4;
						@Pc(563) int temp5;
						@Pc(571) int temp6;
						@Pc(545) int objId;
						if (component.clientCode != 0) {
							if (component.clientCode == 1337 || component.clientCode == 1403 && GlRenderer.enabled) {
								InterfaceList.gameViewportComponent = component;
								InterfaceList.viewportX = componentY;
								gameSceneTooltipX = componentX;
								ScriptRunner.renderGameScene(component.height, component.clientCode == 1403, componentX, component.width, componentY);
								if (GlRenderer.enabled) {
									GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								} else {
									SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								}
								continue;
							}
							if (component.clientCode == 1338) {
								if (!component.buildClickMask()) {
									continue;
								}
								MiniMap.render(rectangle, componentY, componentX, component);
								if (GlRenderer.enabled) {
									GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								} else {
									SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								}
								if (MiniMap.state != 0 && MiniMap.state != 3 || isMenuOpen || drawLeft > ScriptRunner.interfaceMouseX || ScriptRunner.interfaceMouseY < drawTop || ScriptRunner.interfaceMouseX >= drawRight || drawBottom <= ScriptRunner.interfaceMouseY) {
									continue;
								}
								temp1 = ScriptRunner.interfaceMouseX - componentX;
								temp2 = ScriptRunner.interfaceMouseY - componentY;
								temp3 = component.clickMaskStart[temp2];
								if (temp1 < temp3 || temp1 > temp3 + component.clickMaskWidth[temp2]) {
									continue;
								}
								temp2 -= component.height / 2;
								memory = (int) Camera.yawTarget + MiniMap.compassAngleOffset & 0x7FF;
								temp1 -= component.width / 2;
								color = MathUtils.sin[memory];
								cardMemory = MathUtils.cos[memory];
								color = (MiniMap.zoomOffset + 256) * color >> 8;
								cardMemory = (MiniMap.zoomOffset + 256) * cardMemory >> 8;
								objId = cardMemory * temp2 - color * temp1 >> 11;
								temp4 = temp2 * color + temp1 * cardMemory >> 11;
								temp5 = PlayerList.self.xFine + temp4 >> 7;
								temp6 = PlayerList.self.yFine - objId >> 7;
								if (MiniMenu.isTargeting && (MiniMenu.targetMask & 0x40) != 0) {
									@Pc(583) Component targetComponent = InterfaceList.getComponent(MiniMenu.targetInterfaceId, MiniMenu.targetChildId);
									if (targetComponent == null) {
										MiniMenu.cancelTargeting();
									} else {
										MiniMenu.add(MiniMenu.targetCursorId, 1L, MiniMenu.ARROW_SUFFIX, temp5, (short) 11, MiniMenu.targetVerb, temp6);
									}
									continue;
								}
								if (client.game == 1) {
									MiniMenu.add(-1, 1L, JagString.EMPTY, temp5, (short) 36, LocalizedText.FACEHERE, temp6);
								}
								MiniMenu.add(-1, 1L, JagString.EMPTY, temp5, (short) 60, MiniMenu.walkText, temp6);
								continue;
							}
							if (component.clientCode == 1339) {
								if (component.buildClickMask()) {
									renderCompass(componentX, componentY, component, rectangle);
									if (GlRenderer.enabled) {
										GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
									} else {
										SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
									}
								}
								continue;
							}
							if (component.clientCode == 1400) {
								WorldMap.render(componentX, componentY, component.height, component.width);
								InterfaceList.rectangleDirty[rectangle] = true;
								InterfaceList.rectangleRedraw[rectangle] = true;
								if (GlRenderer.enabled) {
									GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								} else {
									SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								}
								continue;
							}
							if (component.clientCode == 1401) {
								renderWorldMapOverview(componentX, component.height, component.width, componentY);
								InterfaceList.rectangleDirty[rectangle] = true;
								InterfaceList.rectangleRedraw[rectangle] = true;
								if (GlRenderer.enabled) {
									GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								} else {
									SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								}
								continue;
							}
							if (component.clientCode == 1402) {
								if (!GlRenderer.enabled) {
									Flames.render(componentX, componentY);
									InterfaceList.rectangleDirty[rectangle] = true;
									InterfaceList.rectangleRedraw[rectangle] = true;
								}
								continue;
							}
							if (component.clientCode == 1405) {
								PluginRepository.Draw();
								InterfaceList.rectangleDirty[rectangle] = true;
								InterfaceList.rectangleRedraw[rectangle] = true;

								//Above are inauthentic changes to call plugin draws and redraw this interface. Below I have left intact all the authentic code.

								if (!Cheat.displayFps) {
									continue;
								}
								temp1 = component.width + componentX;
								temp2 = componentY + 15;
								Fonts.p12Full.renderRight(JagString.concatenate(new JagString[]{Cheat.DEBUG_FPS2, JagString.parseInt((int) GameShell.framesPerSecond)}), temp1, temp2, 16776960, 0);
								temp2 += 15;
								@Pc(795) Runtime runtime = Runtime.getRuntime();
								memory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L / 1024L);
								color = 16776960;
								if (memory > 128) {
									color = 16711680;
								}
								Fonts.p12Full.renderRight(JagString.concatenate(new JagString[]{Cheat.DEBUG_MEM, JagString.parseInt(memory), Cheat.DEBUG_MEM_UNIT}), temp1, temp2, color, 0);
								temp2 += 15;
								if (GlRenderer.enabled) {
									color = 16776960;
									cardMemory = (GlCleaner.onCardTexture + GlCleaner.onCardGeometry + GlCleaner.onCard2d) / 1024 / 1024;
									if (cardMemory > 64) {
										color = 16711680;
									}
									Fonts.p12Full.renderRight(JagString.concatenate(new JagString[]{Cheat.DEBUG_CARD, JagString.parseInt(cardMemory), Cheat.DEBUG_MEM_UNIT}), temp1, temp2, color, 0);
									temp2 += 15;
								}
								cardMemory = 0;
								objId = 0;
								temp4 = 0;
								for (temp5 = 0; temp5 < 28; temp5++) {
									cardMemory += client.js5Providers[temp5].getIndexSize();
									temp4 += client.js5Providers[temp5].getVerifiedGroups();
									objId += client.js5Providers[temp5].getTotalVerifiedGroups();
								}
								temp6 = temp4 * 10000 / cardMemory;
								temp5 = objId * 100 / cardMemory;
								@Pc(968) JagString cacheStatText = JagString.concatenate(new JagString[]{Cheat.DEBUG_CAHE, StringUtils.formatNumber(0, true, 2, temp6), CACHE_STAT_SEPARATOR, JagString.parseInt(temp5), CACHE_STAT_SUFFIX});
								Fonts.p11Full.renderRight(cacheStatText, temp1, temp2, 16776960, 0);
								temp2 += 12;
								InterfaceList.rectangleDirty[rectangle] = true;
								InterfaceList.rectangleRedraw[rectangle] = true;
								continue;
							}
							if (component.clientCode == 1406) {
								tooltipRenderY = componentY;
								LoginManager.tooltipComponent = component;
								tooltipRenderX = componentX;
								continue;
							}
						}
						if (!isMenuOpen) {
							if (component.type == 0 && component.noClickThrough && ScriptRunner.interfaceMouseX >= drawLeft && ScriptRunner.interfaceMouseY >= drawTop && ScriptRunner.interfaceMouseX < drawRight && drawBottom > ScriptRunner.interfaceMouseY && !Cheat.qaOpTest) {
								MiniMenu.size = 1;
								MiniMenu.cursors[0] = MiniMenu.defaultCursorId;
								MiniMenu.ops[0] = LocalizedText.CANCEL;
								MiniMenu.opBases[0] = JagString.EMPTY;
								MiniMenu.actions[0] = 1005;
							}
							if (drawLeft <= ScriptRunner.interfaceMouseX && drawTop <= ScriptRunner.interfaceMouseY && drawRight > ScriptRunner.interfaceMouseX && drawBottom > ScriptRunner.interfaceMouseY) {
								MiniMenu.addComponentEntries(ScriptRunner.interfaceMouseY - componentY, -componentX + ScriptRunner.interfaceMouseX, component);
							}
						}
						if (component.type == 0) {
							if (!component.if3 && InterfaceList.isHidden(component) && InterfaceList.hoveredComponent != component) {
								continue;
							}
							if (!component.if3) {
								if (component.scrollMaxV - component.height < component.scrollY) {
									component.scrollY = component.scrollMaxV - component.height;
								}
								if (component.scrollY < 0) {
									component.scrollY = 0;
								}
							}
							renderComponent(drawLeft, componentY - component.scrollY, -component.scrollX + componentX, components, drawRight, component.id, drawTop, drawBottom, rectangle);
							if (component.createdComponents != null) {
								renderComponent(drawLeft, componentY - component.scrollY, -component.scrollX + componentX, component.createdComponents, drawRight, component.id, drawTop, drawBottom, rectangle);
							}
							@Pc(1186) ComponentPointer openInterface = (ComponentPointer) InterfaceList.openInterfaces.get(component.id);
							if (openInterface != null) {
								if (openInterface.type == 0 && !isMenuOpen && ScriptRunner.interfaceMouseX >= drawLeft && drawTop <= ScriptRunner.interfaceMouseY && drawRight > ScriptRunner.interfaceMouseX && ScriptRunner.interfaceMouseY < drawBottom && !Cheat.qaOpTest) {
									MiniMenu.ops[0] = LocalizedText.CANCEL;
									MiniMenu.size = 1;
									MiniMenu.cursors[0] = MiniMenu.defaultCursorId;
									MiniMenu.actions[0] = 1005;
									MiniMenu.opBases[0] = JagString.EMPTY;
								}
								renderInterface(openInterface.interfaceId, drawLeft, drawRight, componentX, rectangle, drawBottom, drawTop, componentY);
							}
							if (GlRenderer.enabled) {
								GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
							} else {
								SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
								Rasteriser.prepare();
							}
						}
						if (InterfaceList.rectangleDirtySnapshot[rectangle] || Cheat.rectDebug > 1) {
							if (component.type == 0 && !component.if3 && component.scrollMaxV > component.height) {
								renderScrollbar(component.scrollY, component.scrollMaxV, component.width + componentX, componentY, component.height);
							}

							if (component.type != 1) {
								if (component.type == 2) {
									temp1 = 0;
									for (temp2 = 0; temp2 < component.baseHeight; temp2++) {
										for (temp3 = 0; temp3 < component.baseWidth; temp3++) {
											int y = componentY + temp2 * (component.invMarginY + 32);
											int x = (component.invMarginX + 32) * temp3 + componentX;
											if (temp1 < 20) {
												y += component.invOffsetY[temp1];
												x += component.invOffsetX[temp1];
											}
											if (component.objTypes[temp1] > 0) {
												objId = component.objTypes[temp1] - 1;
												if (clipLeft < x + 32 && x < clipRight && clipTop < y + 32 && y < clipBottom || component == InterfaceList.clickedInventoryComponent && InterfaceList.mouseOverInventoryObjectIndex == temp1) {
													@Pc(1476) Sprite sprite;

													if (MiniMenu.itemTargetMode == 1 && MiniMenu.selectedObjSlot == temp1 && component.id == MiniMap.selectedComponentId) {
														sprite = Inv.getObjectSprite(2, objId, component.objDrawText, component.objCounts[temp1], 0);
													} else {
														sprite = Inv.getObjectSprite(1, objId, component.objDrawText, component.objCounts[temp1], 3153952);
													}

													if (Rasteriser.textureHasTransparency) {
														InterfaceList.rectangleDirty[rectangle] = true;
													}

													if (sprite == null) {
														InterfaceList.redraw(component);
													} else if (InterfaceList.clickedInventoryComponent == component && temp1 == InterfaceList.mouseOverInventoryObjectIndex) {
														int dragX = Mouse.lastMouseX - InterfaceList.clickedInventoryComponentX;
														int dragY = Mouse.lastMouseY - InterfaceList.clickedInventoryComponentY;

														if (dragY < 5 && dragY > -5) {
															dragY = 0;
														}

														if (dragX < 5 && dragX > -5) {
															dragX = 0;
														}

														if (InterfaceList.clickedInventoryComponentCycle < 5) {
															dragX = 0;
															dragY = 0;
														}

														// draw dragged icon (at half opacity)
														sprite.renderAlpha(x + dragX, y + dragY, 128);

														if (layer != -1) {
															@Pc(1571) Component scrollParent = components[layer & 0xFFFF];
															@Pc(1577) int top;
															@Pc(1575) int bottom;

															if (GlRenderer.enabled) {
																bottom = GlRaster.clipBottom;
																top = GlRaster.clipTop;
															} else {
																top = SoftwareRaster.clipTop;
																bottom = SoftwareRaster.clipBottom;
															}

															@Pc(1611) int scrollAmount;
															if (top > dragY + y && scrollParent.scrollY > 0) {
																scrollAmount = Protocol.sceneDelta * (top - dragY - y) / 3;
																if (scrollAmount > Protocol.sceneDelta * 10) {
																	scrollAmount = Protocol.sceneDelta * 10;
																}

																if (scrollAmount > scrollParent.scrollY) {
																	scrollAmount = scrollParent.scrollY;
																}

																scrollParent.scrollY -= scrollAmount;
																InterfaceList.clickedInventoryComponentY += scrollAmount;
																InterfaceList.redraw(scrollParent);
															}

															if (bottom < dragY + y + 32 && scrollParent.scrollY < scrollParent.scrollMaxV - scrollParent.height) {
																scrollAmount = (y + dragY + 32 - bottom) * Protocol.sceneDelta / 3;
																if (scrollAmount > Protocol.sceneDelta * 10) {
																	scrollAmount = Protocol.sceneDelta * 10;
																}

																if (scrollParent.scrollMaxV - scrollParent.scrollY - scrollParent.height < scrollAmount) {
																	scrollAmount = scrollParent.scrollMaxV - scrollParent.height - scrollParent.scrollY;
																}

																scrollParent.scrollY += scrollAmount;
																InterfaceList.clickedInventoryComponentY -= scrollAmount;
																InterfaceList.redraw(scrollParent);
															}
														}
													} else if (component == MiniMenu.pressedInventoryComponent && temp1 == MiniMenu.pressedSlotIndex) {
														sprite.renderAlpha(x, y, 128);
													} else {
														sprite.render(x, y);
														// downscale:
														// sprite.renderResized(x, y, 36, 32);
													}
												}
											} else if (component.invSprite != null && temp1 < 20) {
												@Pc(1381) Sprite invSprite = component.getInvSprite(temp1);
												if (invSprite != null) {
													invSprite.render(x, y);
												} else if (Component.loadFailed) {
													InterfaceList.redraw(component);
												}
											}

											temp1++;
										}
									}
									PluginRepository.ComponentDraw(i, component, componentX, componentY);
								} else if (component.type == 3) {
									if (isTrue(component)) {
										temp1 = component.activeColor;
										if (InterfaceList.hoveredComponent == component && component.activeOverColor != 0) {
											temp1 = component.activeOverColor;
										}
									} else {
										temp1 = component.color;
										if (component == InterfaceList.hoveredComponent && component.overColor != 0) {
											temp1 = component.overColor;
										}
									}
									if (alpha == 0) {
										if (component.filled) {
											if (GlRenderer.enabled) {
												GlRaster.fillRect(componentX, componentY, component.width, component.height, temp1);
											} else {
												SoftwareRaster.fillRect(componentX, componentY, component.width, component.height, temp1);
											}
										} else if (GlRenderer.enabled) {
											GlRaster.drawRect(componentX, componentY, component.width, component.height, temp1);
										} else {
											SoftwareRaster.drawRect(componentX, componentY, component.width, component.height, temp1);
										}
									} else if (component.filled) {
										if (GlRenderer.enabled) {
											GlRaster.fillRectAlpha(componentX, componentY, component.width, component.height, temp1, 256 - (alpha & 0xFF));
										} else {
											SoftwareRaster.fillRectAlpha(componentX, componentY, component.width, component.height, temp1, 256 - (alpha & 0xFF));
										}
									} else if (GlRenderer.enabled) {
										GlRaster.drawRectAlpha(componentX, componentY, component.width, component.height, temp1, 256 - (alpha & 0xFF));
									} else {
										SoftwareRaster.drawRectAlpha(componentX, componentY, component.width, component.height, temp1, 256 - (alpha & 0xFF));
									}
									PluginRepository.ComponentDraw(i, component, componentX, componentY);
								} else {
									@Pc(1921) Font font;
									if (component.type == 4) {
										font = component.getFont(Sprites.nameIcons);
										if (font != null) {
											@Pc(1934) JagString displayText = component.text;
											if (isTrue(component)) {
												temp2 = component.activeColor;
												if (InterfaceList.hoveredComponent == component && component.activeOverColor != 0) {
													temp2 = component.activeOverColor;
												}
												if (component.activeText.length() > 0) {
													displayText = component.activeText;
												}
											} else {
												temp2 = component.color;
												if (InterfaceList.hoveredComponent == component && component.overColor != 0) {
													temp2 = component.overColor;
												}
											}
											if (component.if3 && component.objId != -1) {
												@Pc(1989) ObjType objType = ObjTypeList.get(component.objId);
												displayText = objType.name;
												if (displayText == null) {
													displayText = MiniMenu.NULL;
												}
												if ((objType.stackable == 1 || component.objCount != 1) && component.objCount != -1) {
													displayText = JagString.concatenate(new JagString[]{MiniMenu.COLOR_ITEM_ORANGE, displayText, JagString.CLOSE_COLOR_TIMES, formatItemCount(component.objCount)});
												}
											}
											if (pleaseWaitComponent == component) {
												temp2 = component.color;
												displayText = LocalizedText.PLEASEWAIT;
											}
											if (!component.if3) {
												displayText = interpolate(component, displayText);
											}
											font.drawInterfaceText(displayText, componentX, componentY, component.width, component.height, temp2, component.shadowed ? 0 : -1, component.halign, component.valign, component.vpadding);
											PluginRepository.ComponentDraw(i, component, componentX, componentY);
										} else if (Component.loadFailed) {
											InterfaceList.redraw(component);
										}
									} else if (component.type == 5) {
										@Pc(2094) Sprite sprite = null;
										if (component.if3) {
											if (component.objId == -1) {
												sprite = component.getSprite(false);
											} else {
												sprite = Inv.getObjectSprite(component.outlineThickness, component.objId, component.objDrawText, component.objCount, component.shadowColor);
											}

											if (sprite != null) {
												temp2 = sprite.innerWidth;
												temp3 = sprite.innerHeight;

												if (component.spriteTiling) {
													memory = (temp2 + component.width - 1) / temp2;
													color = (component.height + temp3 - 1) / temp3;

													if (GlRenderer.enabled) {
														GlRaster.setClipRegion(componentX, componentY, component.width + componentX, component.height + componentY);

														@Pc(2274) boolean widthPow2 = IntUtils.isPowerOfTwo(sprite.width);
														@Pc(2279) boolean heightPow2 = IntUtils.isPowerOfTwo(sprite.height);
														@Pc(2282) GlSprite glSprite = (GlSprite) sprite;
														if (widthPow2 && heightPow2) {
															if (alpha == 0) {
																glSprite.renderTiled(componentX, componentY, memory, color);
															} else {
																glSprite.renderTiledAlpha(componentX, componentY, 256 - (alpha & 0xFF), memory, color);
															}
															PluginRepository.ComponentDraw(i, component, componentX, componentY);
														} else if (widthPow2) {
															for (temp5 = 0; temp5 < color; temp5++) {
																if (alpha == 0) {
																	glSprite.renderTiled(componentX, temp5 * temp3 + componentY, memory, 1);
																} else {
																	glSprite.renderTiledAlpha(componentX, componentY + temp5 * temp3, -(alpha & 0xFF) + 256, memory, 1);
																}
															}
															PluginRepository.ComponentDraw(i, component, componentX, componentY);
														} else if (heightPow2) {
															for (temp5 = 0; temp5 < memory; temp5++) {
																if (alpha == 0) {
																	glSprite.renderTiled(temp2 * temp5 + componentX, componentY, 1, color);
																} else {
																	glSprite.renderTiledAlpha(temp2 * temp5 + componentX, componentY, 256 - (alpha & 0xFF), 1, color);
																}
															}
															PluginRepository.ComponentDraw(i, component, componentX, componentY);
														} else {
															for (temp5 = 0; temp5 < memory; temp5++) {
																for (temp6 = 0; temp6 < color; temp6++) {
																	if (alpha == 0) {
																		sprite.render(componentX + temp2 * temp5, temp3 * temp6 + componentY);
																	} else {
																		sprite.renderAlpha(temp5 * temp2 + componentX, temp3 * temp6 + componentY, 256 - (alpha & 0xFF));
																	}
																}
															}
															PluginRepository.ComponentDraw(i, component, componentX + temp2, temp3 + componentY);
														}

														GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
													} else {
														SoftwareRaster.shrinkClip(componentX, componentY, componentX + component.width, componentY - -component.height);
														for (cardMemory = 0; cardMemory < memory; cardMemory++) {
															for (temp4 = 0; temp4 < color; temp4++) {
																if (component.angle2d != 0) {
																	sprite.renderAngled(componentY + temp3 * temp4 + temp3 / 2, component.angle2d, 4096, cardMemory * temp2 + componentX + temp2 / 2);
																} else if (alpha == 0) {
																	sprite.render(cardMemory * temp2 + componentX, temp3 * temp4 + componentY);
																} else {
																	sprite.renderAlpha(cardMemory * temp2 + componentX, componentY + temp4 * temp3, 256 - (alpha & 0xFF));
																}
															}
														}
														PluginRepository.ComponentDraw(i, component, componentX + temp2, componentY + temp3);

														SoftwareRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
													}
												} else {
													memory = component.width * 4096 / temp2;
													if (component.angle2d != 0) {
														sprite.renderAngled(componentY + component.height / 2, component.angle2d, memory, componentX + component.width / 2);
													} else if (alpha != 0) {
														sprite.renderAlpha(componentX, componentY, component.width, component.height, 256 - (alpha & 0xFF));
													} else if (temp2 == component.width && temp3 == component.height) {
														sprite.render(componentX, componentY);
													} else {
														// render icons in a container i.e bank icons
														sprite.renderResized(componentX, componentY, component.width, component.height);
													}
													PluginRepository.ComponentDraw(i, component, componentX, componentY);
												}
											} else if (Component.loadFailed) {
												InterfaceList.redraw(component);
											}
										} else {
											sprite = component.getSprite(isTrue(component));
											if (sprite != null) {
												sprite.render(componentX, componentY);
											} else if (Component.loadFailed) {
												InterfaceList.redraw(component);
											}
										}
									} else {
										@Pc(2611) ObjType objDef;
										if (component.type == 6) {
											@Pc(2587) boolean active = isTrue(component);
											@Pc(2589) Model model = null;
											if (active) {
												temp2 = component.activeModelSeqId;
											} else {
												temp2 = component.modelSeqId;
											}
											memory = 0;
											if (component.objId != -1) {
												objDef = ObjTypeList.get(component.objId);
												if (objDef != null) {
													objDef = objDef.getCountVariant(component.objCount);
													@Pc(2630) SeqType seqType = temp2 == -1 ? null : SeqTypeList.get(temp2);
													model = objDef.getModel(component.seqNextFrame, component.seqCycle, seqType, 1, component.seqFrame);
													if (model == null) {
														InterfaceList.redraw(component);
													} else {
														memory = -model.getMinY() / 2;
													}
												}
											} else if (component.modelType == 5) {
												if (component.modelId == -1) {
													model = PlayerAppearance.DEFAULT.getBodyModel(null, -1, null, null, 0, -1, 0, -1, -1);
												} else {
													color = component.modelId & 0x7FF;
													if (color == PlayerList.selfId) {
														color = 2047;
													}
													@Pc(2751) Player player = PlayerList.players[color];
													@Pc(2760) SeqType playerSeqType = temp2 == -1 ? null : SeqTypeList.get(temp2);
													if (player != null && (int) player.username.encode37() << 11 == (component.modelId & 0xFFFFF800)) {
														model = player.appearance.getBodyModel(null, -1, null, playerSeqType, 0, -1, 0, component.seqFrame, 0);
													}
												}
											} else if (temp2 == -1) {
												model = component.getModel(-1, null, -1, 0, active, PlayerList.self.appearance);
												if (model == null && Component.loadFailed) {
													InterfaceList.redraw(component);
												}
											} else {
												@Pc(2689) SeqType seqType = SeqTypeList.get(temp2);
												model = component.getModel(component.seqNextFrame, seqType, component.seqFrame, component.seqCycle, active, PlayerList.self.appearance);
												if (model == null && Component.loadFailed) {
													InterfaceList.redraw(component);
												}
											}
											if (model != null) {
												if (component.modelViewportWidth > 0) {
													color = (component.width << 8) / component.modelViewportWidth;
												} else {
													color = 256;
												}
												if (component.modelViewportHeight <= 0) {
													cardMemory = 256;
												} else {
													cardMemory = (component.height << 8) / component.modelViewportHeight;
												}
												temp4 = componentX + component.width / 2 + (color * component.modelOriginX >> 8);
												objId = component.height / 2 + componentY + (cardMemory * component.modelOriginY >> 8);
												if (GlRenderer.enabled) {
													if (component.modelOrtho) {
														GlRenderer.setupModelPreview(temp4, objId, component.modelZoom, component.modelViewAngle, color, cardMemory);
													} else {
														GlRenderer.setFullscreenCamera(temp4, objId, color, cardMemory);
														GlRenderer.setDepthBias(component.modelNearClip, (float) component.modelViewAngle * 1.5F);
													}
													GlRenderer.restoreLighting();
													GlRenderer.setDepthTestEnabled(true);
													GlRenderer.setFogEnabled(false);
													FogManager.init(Preferences.brightness);
													if (ScriptRunner.glSceneNeedsRender) {
														GlRaster.resetClipRegion();
														GlRenderer.clearDepthBuffer();
														GlRaster.setClip(clipLeft, clipTop, clipRight, clipBottom);
														ScriptRunner.glSceneNeedsRender = false;
													}
													if (component.modelTransparent) {
														GlRenderer.disableDepthMask();
													}
													temp5 = MathUtils.sin[component.modelXAngle] * component.modelZoom >> 16;
													temp6 = component.modelZoom * MathUtils.cos[component.modelXAngle] >> 16;
													if (component.if3) {
														model.setCamera(component.modelYAngle, component.modelYOffset, component.modelXAngle, component.modelXOffset, component.modelZOffset + temp5 + memory, component.modelZOffset + temp6, -1L);
													} else {
														model.setCamera(component.modelYAngle, 0, component.modelXAngle, 0, temp5, temp6, -1L);
													}
													if (component.modelTransparent) {
														GlRenderer.enableDepthMask();
													}
												} else {
													Rasteriser.setBounds(temp4, objId);
													temp5 = MathUtils.sin[component.modelXAngle] * component.modelZoom >> 16;
													temp6 = component.modelZoom * MathUtils.cos[component.modelXAngle] >> 16;
													if (!component.if3) {
														model.setCamera(component.modelYAngle, 0, component.modelXAngle, 0, temp5, temp6, -1L);
													} else if (component.modelOrtho) {
														((SoftwareModel) model).renderOnInterface(component.modelYAngle, component.modelYOffset, component.modelXAngle, component.modelXOffset, component.modelZOffset + memory + temp5, temp6 + component.modelZOffset, component.modelZoom);
													} else {
														model.setCamera(component.modelYAngle, component.modelYOffset, component.modelXAngle, component.modelXOffset, component.modelZOffset + temp5 + memory, temp6 + component.modelZOffset, -1L);
													}
													Rasteriser.prepareOffsets();
												}
											}
											PluginRepository.ComponentDraw(i, component, componentX + component.width / 2, componentY + component.height / 2);
										} else {
											if (component.type == 7) {
												font = component.getFont(Sprites.nameIcons);
												if (font == null) {
													if (Component.loadFailed) {
														InterfaceList.redraw(component);
													}
													continue;
												}
												temp2 = 0;
												for (temp3 = 0; temp3 < component.baseHeight; temp3++) {
													for (memory = 0; memory < component.baseWidth; memory++) {
														if (component.objTypes[temp2] > 0) {
															objDef = ObjTypeList.get(component.objTypes[temp2] - 1);
															@Pc(3159) JagString itemText;
															if (objDef.stackable != 1 && component.objCounts[temp2] == 1) {
																itemText = JagString.concatenate(new JagString[]{MiniMenu.COLOR_ITEM_ORANGE, objDef.name, JagString.CLOSE_COLOR});
															} else {
																itemText = JagString.concatenate(new JagString[]{MiniMenu.COLOR_ITEM_ORANGE, objDef.name, JagString.CLOSE_COLOR_TIMES, formatItemCount(component.objCounts[temp2])});
															}
															temp4 = componentX + memory * (component.invMarginX + 115);
															objId = (component.invMarginY + 12) * temp3 + componentY;
															if (component.halign == 0) {
																font.renderLeft(itemText, temp4, objId, component.color, component.shadowed ? 0 : -1);
															} else if (component.halign == 1) {
																font.renderCenter(itemText, temp4 + 57, objId, component.color, component.shadowed ? 0 : -1);
															} else {
																font.renderRight(itemText, temp4 + 115 - 1, objId, component.color, component.shadowed ? 0 : -1);
															}
														}
														temp2++;
													}
												}
												PluginRepository.ComponentDraw(i, component, componentX + component.invMarginX + 115, componentY + component.invMarginY + 12);
											}
											if (component.type == 8 && Protocol.tooltipComponent == component && Protocol.tooltipTimer == TOOLTIP_DISPLAY_DELAY) {
												temp2 = 0;
												temp1 = 0;
												@Pc(3297) JagString tooltipText = component.text;
												@Pc(3299) Font tooltipFont = Fonts.p12Full;
												tooltipText = interpolate(component, tooltipText);
												@Pc(3325) JagString tooltipLine;
												while (tooltipText.length() > 0) {
													cardMemory = tooltipText.indexOf(JagString.LINE_BREAK);
													if (cardMemory == -1) {
														tooltipLine = tooltipText;
														tooltipText = JagString.EMPTY;
													} else {
														tooltipLine = tooltipText.substring(cardMemory, 0);
														tooltipText = tooltipText.substring(cardMemory + 4);
													}
													temp4 = tooltipFont.getStringWidth(tooltipLine);
													temp2 += tooltipFont.lineHeight + 1;
													if (temp1 < temp4) {
														temp1 = temp4;
													}
												}
												temp4 = componentY + component.height + 5;
												temp1 += 6;
												temp2 += 7;
												if (temp4 + temp2 > clipBottom) {
													temp4 = clipBottom - temp2;
												}
												cardMemory = componentX + component.width - temp1 - 5;
												if (cardMemory < componentX + 5) {
													cardMemory = componentX + 5;
												}
												if (temp1 + cardMemory > clipRight) {
													cardMemory = clipRight - temp1;
												}
												if (GlRenderer.enabled) {
													GlRaster.fillRect(cardMemory, temp4, temp1, temp2, 16777120);
													GlRaster.drawRect(cardMemory, temp4, temp1, temp2, 0);
												} else {
													SoftwareRaster.fillRect(cardMemory, temp4, temp1, temp2, 16777120);
													SoftwareRaster.drawRect(cardMemory, temp4, temp1, temp2, 0);
												}
												tooltipText = component.text;
												objId = temp4 + tooltipFont.lineHeight + 2;
												tooltipText = interpolate(component, tooltipText);
												while (tooltipText.length() > 0) {
													temp5 = tooltipText.indexOf(JagString.LINE_BREAK);
													if (temp5 == -1) {
														tooltipLine = tooltipText;
														tooltipText = JagString.EMPTY;
													} else {
														tooltipLine = tooltipText.substring(temp5, 0);
														tooltipText = tooltipText.substring(temp5 + 4);
													}
													tooltipFont.renderLeft(tooltipLine, cardMemory + 3, objId, 0, -1);
													objId += tooltipFont.lineHeight + 1;
												}
												PluginRepository.ComponentDraw(i, component, cardMemory + 3, objId);
											}
											if (component.type == 9) {
												if (component.lineFlipped) {
													temp3 = componentX + component.width;
													temp2 = componentY + component.height;
													memory = componentY;
												} else {
													temp2 = componentY;
													memory = componentY + component.height;
													temp3 = componentX + component.width;
												}
												if (component.lineWidth == 1) {
													if (GlRenderer.enabled) {
														GlRaster.drawLine(componentX, temp2, temp3, memory, component.color);
													} else {
														SoftwareRaster.drawLine(componentX, temp2, temp3, memory, component.color);
													}
												} else if (GlRenderer.enabled) {
													GlRaster.drawThickLine(componentX, temp2, temp3, memory, component.color, component.lineWidth);
												} else {
													SoftwareRaster.drawThickLine(componentX, temp2, temp3, memory, component.color, component.lineWidth);
												}
												PluginRepository.ComponentDraw(i, component, temp3, temp2);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ag", name = "a", descriptor = "(IIIIIIIII)V")
	public static void renderInterface(@OriginalArg(1) int interfaceId, @OriginalArg(2) int clipLeft, @OriginalArg(3) int clipRight, @OriginalArg(4) int parentX, @OriginalArg(5) int rectangle, @OriginalArg(6) int clipBottom, @OriginalArg(7) int clipTop, @OriginalArg(8) int parentY) {
		if (InterfaceList.load(interfaceId)) {
			renderComponent(clipLeft, parentY, parentX, InterfaceList.components[interfaceId], clipRight, -1, clipTop, clipBottom, rectangle);
		} else if (rectangle == -1) {
			for (@Pc(27) int i = 0; i < 100; i++) {
				InterfaceList.rectangleDirty[i] = true;
			}
		} else {
			InterfaceList.rectangleDirty[rectangle] = true;
		}
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(Z)V")
	public static void renderTopLevelInterface() {
		deferredDragComponents = null;
		renderInterface(InterfaceList.topLevelInterface, 0, GameShell.canvasWidth, 0, -1, GameShell.canvasHeight, 0, 0);
		if (deferredDragComponents != null) {
			renderComponent(0, deferredDragRenderY, deferredDragRenderX, deferredDragComponents, GameShell.canvasWidth, -1412584499, 0, GameShell.canvasHeight, dragParentComponent.rectangle);
			deferredDragComponents = null;
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IILclient!be;IB)V")
	public static void renderCompass(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) Component component, @OriginalArg(3) int rectangle) {
		if (GlRenderer.enabled) {
			GlRaster.setClip(x, y, component.width + x, component.height + y);
		}
		if (MiniMap.state >= 3) {
			if (GlRenderer.enabled) {
				@Pc(44) Sprite sprite = component.getSprite(false);
				if (sprite != null) {
					sprite.render(x, y);
				}
			} else {
				SoftwareRaster.clearMaskedRegion(x, y, component.clickMaskStart, component.clickMaskWidth);
			}
		} else if (GlRenderer.enabled) {
			((GlSprite) Sprites.compass).renderRotatedTransparent(x, y, component.width, component.height, Sprites.compass.width / 2, Sprites.compass.height / 2, (int) Camera.yawTarget, 256, (GlSprite) component.getSprite(false));
		} else {
			((SoftwareSprite) Sprites.compass).renderRotated(x, y, component.width, component.height, Sprites.compass.width / 2, Sprites.compass.height / 2, (int) Camera.yawTarget, component.clickMaskStart, component.clickMaskWidth);
		}
		InterfaceList.rectangleRedraw[rectangle] = true;
	}

	@OriginalMember(owner = "client!fn", name = "a", descriptor = "(BIIIII)V")
	public static void renderScrollbar(@OriginalArg(1) int scrollY, @OriginalArg(2) int scrollMax, @OriginalArg(3) int x, @OriginalArg(4) int y, @OriginalArg(5) int height) {
		Sprites.scrollbars[0].renderTransparent(x, y);
		Sprites.scrollbars[1].renderTransparent(x, height + y - 16);
		@Pc(35) int thumbHeight = height * (height - 32) / scrollMax;
		if (thumbHeight < 8) {
			thumbHeight = 8;
		}
		@Pc(54) int thumbY = scrollY * (height - thumbHeight - 32) / (scrollMax - height);
		if (!GlRenderer.enabled) {
			SoftwareRaster.fillRect(x, y + 16, 16, height - 32, SCROLLBAR_TRACK_COLOR);
			SoftwareRaster.fillRect(x, thumbY + y + 16, 16, thumbHeight, SCROLLBAR_THUMB_COLOR);
			SoftwareRaster.drawVerticalLine(x, thumbY + y + 16, thumbHeight, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
			SoftwareRaster.drawVerticalLine(x + 1, thumbY + 16 + y, thumbHeight, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
			SoftwareRaster.drawHorizontalLine(x, y + thumbY + 16, 16, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
			SoftwareRaster.drawHorizontalLine(x, y + thumbY + 17, 16, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
			SoftwareRaster.drawVerticalLine(x + 15, thumbY + 16 + y, thumbHeight, SCROLLBAR_THUMB_SHADOW_COLOR);
			SoftwareRaster.drawVerticalLine(x + 14, y - -17 - -thumbY, thumbHeight - 1, SCROLLBAR_THUMB_SHADOW_COLOR);
			SoftwareRaster.drawHorizontalLine(x, thumbHeight + y + thumbY + 15, 16, SCROLLBAR_THUMB_SHADOW_COLOR);
			SoftwareRaster.drawHorizontalLine(x + 1, thumbHeight + y - (-thumbY + -14), 15, SCROLLBAR_THUMB_SHADOW_COLOR);
			return;
		}
		GlRaster.fillRect(x, y + 16, 16, height - 32, SCROLLBAR_TRACK_COLOR);
		GlRaster.fillRect(x, y + thumbY + 16, 16, thumbHeight, SCROLLBAR_THUMB_COLOR);
		GlRaster.drawVerticalLine(x, thumbY + y + 16, thumbHeight, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
		GlRaster.drawVerticalLine(x + 1, thumbY + 16 + y, thumbHeight, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
		GlRaster.drawHorizontalLine(x, thumbY + y + 16, 16, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
		GlRaster.drawHorizontalLine(x, thumbY + y + 17, 16, SCROLLBAR_THUMB_HIGHLIGHT_COLOR);
		GlRaster.drawVerticalLine(x + 15, y + (16 - -thumbY), thumbHeight, SCROLLBAR_THUMB_SHADOW_COLOR);
		GlRaster.drawVerticalLine(x + 14, y - -thumbY + 17, thumbHeight - 1, SCROLLBAR_THUMB_SHADOW_COLOR);
		GlRaster.drawHorizontalLine(x, thumbHeight + y + thumbY + 15, 16, SCROLLBAR_THUMB_SHADOW_COLOR);
		GlRaster.drawHorizontalLine(x + 1, y + 14 - -thumbY + thumbHeight, 15, SCROLLBAR_THUMB_SHADOW_COLOR);
	}

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "(BLclient!be;)V")
	public static void applyClientCode(@OriginalArg(1) Component component) {
		@Pc(16) int clientCode = component.clientCode;
		if (clientCode == 324) {
			if (cachedDefaultSpriteId == -1) {
				cachedDefaultSpriteId = component.spriteId;
				cachedActiveSpriteId = component.activeSpriteId;
			}
			if (PlayerAppearance.DEFAULT.gender) {
				component.spriteId = cachedDefaultSpriteId;
			} else {
				component.spriteId = cachedActiveSpriteId;
			}
		} else if (clientCode == 325) {
			if (cachedDefaultSpriteId == -1) {
				cachedActiveSpriteId = component.activeSpriteId;
				cachedDefaultSpriteId = component.spriteId;
			}
			if (PlayerAppearance.DEFAULT.gender) {
				component.spriteId = cachedActiveSpriteId;
			} else {
				component.spriteId = cachedDefaultSpriteId;
			}
		} else if (clientCode == 327) {
			component.modelXAngle = 150;
			component.modelYAngle = (int) (Math.sin((double) client.loop / 40.0D) * 256.0D) & 0x7FF;
			component.modelType = 5;
			component.modelId = -1;
		} else if (clientCode == 328) {
			if (PlayerList.self.username == null) {
				component.modelId = 0;
			} else {
				component.modelXAngle = 150;
				component.modelYAngle = (int) (Math.sin((double) client.loop / 40.0D) * 256.0D) & 0x7FF;
				component.modelType = 5;
				component.modelId = ((int) PlayerList.self.username.encode37() << 11) + 2047;
				component.seqNextFrame = PlayerList.self.movementSeqNextFrame;
				component.seqCycle = 0;
				component.modelSeqId = PlayerList.self.movementSeqId;
				component.seqFrame = PlayerList.self.movementSeqFrame;
			}
		}
	}

	@OriginalMember(owner = "client!fi", name = "a", descriptor = "(BI)Lclient!na;")
	public static JagString formatItemCount(@OriginalArg(1) int count) {
		@Pc(9) JagString str = JagString.parseInt(count);
		for (@Pc(21) int i = str.length() - 3; i > 0; i -= 3) {
			str = JagString.concatenate(new JagString[]{str.substring(i, 0), JagString.COMMA, str.substring(i)});
		}
		if (str.length() > 9) {
			return JagString.concatenate(new JagString[]{JagString.GREEN_COLOR_PREFIX, str.substring(str.length() - 8, 0), LocalizedText.MILLION_SHORT, MiniMenu.OPEN_PARENTHESIS, str, JagString.CLOSE_PAREN_CLOSE_COLOR});
		} else if (str.length() > 6) {
			return JagString.concatenate(new JagString[]{JagString.WHITE_COLOR_PREFIX, str.substring(str.length() - 4, 0), LocalizedText.THOUSAND_SHORT, MiniMenu.OPEN_PARENTHESIS, str, JagString.CLOSE_PAREN_CLOSE_COLOR});
		} else {
			return JagString.concatenate(new JagString[]{JagString.YELLOW_COLOR_PREFIX, str, JagString.CLOSE_COLOR});
		}
	}

	@OriginalMember(owner = "client!a", name = "a", descriptor = "(IIIII)V")
	public static void renderWorldMapOverview(@OriginalArg(0) int x, @OriginalArg(1) int height, @OriginalArg(2) int width, @OriginalArg(4) int y) {
		if (GlRenderer.enabled) {
			GlRaster.setClip(x, y, width + x, height + y);
			GlRaster.fillRect(x, y, width, height, 0);
		} else {
			SoftwareRaster.setClip(x, y, width + x, y + height);
			SoftwareRaster.fillRect(x, y, width, height, 0);
		}
		if (WorldMap.loadPercentage < 100) {
			return;
		}
		if (WorldMap.overviewSprite == null || width != WorldMap.overviewSprite.width || WorldMap.overviewSprite.height != height) {
			@Pc(63) SoftwareSprite softwareSprite = new SoftwareSprite(width, height);
			SoftwareRaster.setSize(softwareSprite.pixels, width, height);
			WorldMap.renderMapViewport(width, 0, WorldMap.width, 0, 0, WorldMap.length, height, 0);
			if (GlRenderer.enabled) {
				WorldMap.overviewSprite = new GlSprite(softwareSprite);
			} else {
				WorldMap.overviewSprite = softwareSprite;
			}
			if (GlRenderer.enabled) {
				SoftwareRaster.pixels = null;
			} else {
				SoftwareRaster.frameBuffer.makeTarget();
			}
		}
		WorldMap.overviewSprite.drawPixels(x, y);
		@Pc(147) int viewY = height * worldMapViewportY / WorldMap.length + y;
		@Pc(153) int viewH = WorldMap.viewportHeight * height / WorldMap.length;
		@Pc(161) int viewX = x + width * worldMapViewportX / WorldMap.width;
		@Pc(167) int viewW = width * WorldMap.viewportWidth / WorldMap.width;
		@Pc(169) int color = 16711680;
		if (client.game == 1) {
			color = 16777215;
		}
		if (GlRenderer.enabled) {
			GlRaster.fillRectAlpha(viewX, viewY, viewW, viewH, color, 128);
			GlRaster.drawRect(viewX, viewY, viewW, viewH, color);
		} else {
			SoftwareRaster.fillRectAlpha(viewX, viewY, viewW, viewH, color, 128);
			SoftwareRaster.drawRect(viewX, viewY, viewW, viewH, color);
		}
		if (WorldMap.highlightPulseCount <= 0) {
			return;
		}
		@Pc(225) int alpha;
		if (mapHighlightPulseCounter > 10) {
			alpha = (20 - mapHighlightPulseCounter) * 25;
		} else {
			alpha = mapHighlightPulseCounter * 25;
		}
		for (@Pc(238) MapElement element = (MapElement) WorldMap.mapElements.head(); element != null; element = (MapElement) WorldMap.mapElements.next()) {
			if (element.id == WorldMap.highlightedMapFunction) {
				@Pc(258) int dotY = y + element.mapY * height / WorldMap.length;
				@Pc(267) int dotX = width * element.mapX / WorldMap.width + x;
				if (GlRenderer.enabled) {
					GlRaster.fillRectAlpha(dotX - 2, dotY + -2, 4, 4, 16776960, alpha);
				} else {
					SoftwareRaster.fillRectAlpha(dotX - 2, dotY + -2, 4, 4, 16776960, alpha);
				}
			}
		}
	}

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(IIILclient!be;)V")
	public static void startComponentDrag(@OriginalArg(0) int mouseY, @OriginalArg(1) int mouseX, @OriginalArg(3) Component component) {
		if (draggedComponent != null || isMenuOpen || (component == null || getDragParent(component) == null)) {
			return;
		}
		draggedComponent = component;
		dragParentComponent = getDragParent(component);
		dragStartMouseX = mouseX;
		isDragging = false;
		dragElapsedTicks = 0;
		dragStartMouseY = mouseY;
	}

	@OriginalMember(owner = "client!ha", name = "a", descriptor = "(ILclient!be;)Lclient!be;")
	public static Component getDragParent(@OriginalArg(1) Component component) {
		@Pc(12) Component parent = InterfaceList.getDragRenderParent(component);
		if (parent == null) {
			parent = component.dragParent;
		}
		return parent;
	}

	@OriginalMember(owner = "client!ac", name = "b", descriptor = "(I)V")
	public static void updateComponentDrag() {
		InterfaceList.redraw(draggedComponent);
		dragElapsedTicks++;
		if (InterfaceList.dragSourceFound && InterfaceList.dragParentFound) {
			@Pc(30) int dragX = Mouse.lastMouseX;
			dragX -= dragStartMouseX;
			if (dragBoundsMinX > dragX) {
				dragX = dragBoundsMinX;
			}
			@Pc(41) int dragY = Mouse.lastMouseY;
			if (dragBoundsMinX + dragParentComponent.width < dragX - -draggedComponent.width) {
				dragX = dragBoundsMinX + dragParentComponent.width - draggedComponent.width;
			}
			dragY -= dragStartMouseY;
			if (dragY < InterfaceList.dragParentY) {
				dragY = InterfaceList.dragParentY;
			}
			if (InterfaceList.dragParentY + dragParentComponent.height < dragY - -draggedComponent.height) {
				dragY = InterfaceList.dragParentY + dragParentComponent.height - draggedComponent.height;
			}
			@Pc(109) int deltaY = dragY - InterfaceList.dragSourceY;
			@Pc(114) int deltaX = dragX - InterfaceList.dragSourceX;
			@Pc(122) int scrolledX = dragX + dragParentComponent.scrollX - dragBoundsMinX;
			@Pc(130) int scrolledY = dragParentComponent.scrollY + dragY - InterfaceList.dragParentY;
			@Pc(133) int deadzone = draggedComponent.dragDeadzone;
			if (dragElapsedTicks > draggedComponent.dragDeadtime && (deadzone < deltaX || -deadzone > deltaX || deltaY > deadzone || deltaY < -deadzone)) {
				isDragging = true;
			}
			@Pc(176) HookRequest hookRequest;
			if (draggedComponent.onDragStart != null && isDragging) {
				hookRequest = new HookRequest();
				hookRequest.source = draggedComponent;
				hookRequest.arguments = draggedComponent.onDragStart;
				hookRequest.mouseX = scrolledX;
				hookRequest.mouseY = scrolledY;
				ScriptRunner.run(hookRequest);
			}
			if (Mouse.pressedButton == 0) {
				if (isDragging) {
					if (draggedComponent.onDragRelease != null) {
						hookRequest = new HookRequest();
						hookRequest.mouseY = scrolledY;
						hookRequest.target = InterfaceList.dragTargetComponent;
						hookRequest.mouseX = scrolledX;
						hookRequest.arguments = draggedComponent.onDragRelease;
						hookRequest.source = draggedComponent;
						ScriptRunner.run(hookRequest);
					}
					if (InterfaceList.dragTargetComponent != null && InterfaceList.getDragRenderParent(draggedComponent) != null) {
						Protocol.outboundBuffer.p1isaac(79);
						Protocol.outboundBuffer.mp4(draggedComponent.id);
						Protocol.outboundBuffer.ip2(InterfaceList.dragTargetComponent.createdComponentId);
						Protocol.outboundBuffer.p4(InterfaceList.dragTargetComponent.id);
						Protocol.outboundBuffer.ip2(draggedComponent.createdComponentId);
					}
				} else if ((VarpDomain.mouseButtons == 1 || MiniMenu.isComponentOptionAction(MiniMenu.size - 1)) && MiniMenu.size > 2) {
					ScriptRunner.layoutMiniMenu();
				} else if (MiniMenu.size > 0) {
					MiniMenu.processClick();
				}
				draggedComponent = null;
			}
		} else if (dragElapsedTicks > 1) {
			draggedComponent = null;
		}
	}
}
