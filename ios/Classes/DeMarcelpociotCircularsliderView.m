/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "DeMarcelpociotCircularsliderView.h"

@implementation DeMarcelpociotCircularsliderView


- (void)initializeState
{
    
    [self createSlider];
    [super initializeState];
}



- (void)createSlider
{
    if( sliderView == nil ){
        NSLog(@"[ERROR] create Slider View");
        
        sliderView = [[EFCircularSlider alloc] initWithFrame:self.bounds];
        [sliderView addTarget:self action:@selector(valueChanged:) forControlEvents:UIControlEventValueChanged];
        [sliderView addTarget:self action:@selector(touchStarted:) forControlEvents:UIControlEventTouchDown];
        [sliderView addTarget:self action:@selector(touchEnded:) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:sliderView];
        [sliderView setCurrentValue:10.0f];
    }
}


-(EFCircularSlider*)sliderView
{
    if( sliderView == nil ){
        return sliderView;
    }
    else {
        return nil;
    }
}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    NSLog(@"[VIEW LIFECYCLE EVENT] frameSizeChanged");

    if (sliderView != nil) {
        NSLog(@"[ERROR] frameSizeChanged not null");

        // You must call the special method 'setView:positionRect' against
        // the TiUtils helper class. This method will correctly layout your
        // child view within the correct layout boundaries of the new bounds
        // of your view.
        [TiUtils setView:sliderView positionRect:bounds];
       
    }
    [super frameSizeChanged:frame bounds:bounds];

}

-(void)setMinimumValue_:(id)minimum
{
    [sliderView setMinimumValue:[TiUtils floatValue:minimum def:0.0f]];
}

-(void)setMaximumValue_:(id)maximum
{
    [sliderView setMaximumValue:[TiUtils floatValue:maximum def:100.0f]];
}

-(void)setValue_:(id)value
{
    [sliderView setCurrentValue:[TiUtils floatValue:value def:0.0f]];
}

-(void)setLineWidth_:(id)value
{
    [sliderView setLineWidth:[TiUtils floatValue:value def:0.0f]];
}

-(void)setHandleColor_:(id)value
{
    [sliderView setHandleColor:[[TiUtils colorValue:value] _color]];
}

-(void)setUnfilledColor_:(id)value
{
    [sliderView setUnfilledColor:[[TiUtils colorValue:value] _color]];
}

-(void)setFilledColor_:(id)value
{
    [sliderView setFilledColor:[[TiUtils colorValue:value] _color]];
}

-(void)setInnerMarkingLabels_:(NSArray *)value
{
    [sliderView setInnerMarkingLabels:value];
}

-(void)setLabelFont_:(id)value
{
    [sliderView setLabelFont:[TiUtils fontValue:value].font];
}

-(void)setLabelColor_:(id)value
{
    [sliderView setLabelColor:[[TiUtils colorValue:value] _color]];
}

-(void)setLabelDisplacement_:(id)value
{
    [sliderView setLabelDisplacement:[TiUtils floatValue:value]];
}

MAKE_SYSTEM_PROP(BIG_CIRCLE, EFBigCircle);
MAKE_SYSTEM_PROP(SEMI_TRANSPARENT_WHITE_CIRCLE, EFSemiTransparentWhiteCircle);
MAKE_SYSTEM_PROP(SEMI_TRANSPARENT_BLACK_CIRCLE, EFSemiTransparentBlackCircle);
MAKE_SYSTEM_PROP(DOUBLE_CIRCLE_OPEN_CENTER, EFDoubleCircleWithOpenCenter);
MAKE_SYSTEM_PROP(DOUBLE_CIRCLE_CLOSED_CENTER,EFDoubleCircleWithClosedCenter);

-(void)setHandleType_:(id)value
{
    NSLog(@"Handle Type: %@",value);
    [sliderView setHandleType:value];
}


-(void)valueChanged:(EFCircularSlider*)slider {
    [self.proxy fireEvent:@"change" withObject:@{
        @"value": NUMFLOAT([slider currentValue])
    }];
}

-(void)touchStarted:(EFCircularSlider*)slider {
    [self.proxy fireEvent:@"touchstart" withObject:@{
        @"value": NUMFLOAT([slider currentValue])
    }];
}

-(void)touchEnded:(EFCircularSlider*)slider {
    [self.proxy fireEvent:@"touchend" withObject:@{
        @"value": NUMFLOAT([slider currentValue])
    }];
}

@end
