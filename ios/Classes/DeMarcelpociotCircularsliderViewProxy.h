/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import <TitaniumKit/TiViewProxy.h>
#import "EFCircularSlider.h"

@interface DeMarcelpociotCircularsliderViewProxy : TiViewProxy {

}

-(void)valueChanged:(EFCircularSlider*)slider;
-(void)touchStarted:(EFCircularSlider*)slider;
-(void)touchEnded:(EFCircularSlider*)slider;

@end
